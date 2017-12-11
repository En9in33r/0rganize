package com.x_c0re.a0rganize;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class CompletedTasksFragment extends ListFragment
{
    public static ArrayList<CompletedTask> completedTaskData = new ArrayList<>();
    CompletedTasksAdapter completedTasksAdapter;
    CompletedTaskFinder finder;

    public static String current_login;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        completedTasksAdapter = new CompletedTasksAdapter(getActivity(), completedTaskData);
        setListAdapter(completedTasksAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        try
        {
            finder = new CompletedTaskFinder();
            finder.execute(MainActivity.current_id);
            String completed_tasks = finder.get();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CompletedTaskJSON[] completedTaskJSON = gson.fromJson(completed_tasks, CompletedTaskJSON[].class);
            for (int i = 0; i <= completedTaskJSON.length - 1; i++)
            {
                completedTaskData.add(new CompletedTask(current_login, completedTaskJSON[i].text,
                        completedTaskJSON[i].proof_image, completedTaskJSON[i].post_rating));
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_completed, container, false);
    }

    @Override
    public void onDestroyView()
    {
        completedTaskData.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        completedTaskData.clear();
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        completedTaskData.clear();
        super.onDetach();
    }

    private static class CompletedTaskFinder extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/" + strings[0] +
                    "/completeds");
            return request.body();
        }
    }
}
