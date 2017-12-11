package com.x_c0re.a0rganize;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FailedTasksFragment extends ListFragment
{
    public static ArrayList<FailedTask> failedTasksData = new ArrayList<>();
    FailedTasksAdapter failed_adapter;
    public static String current_login;

    FailedTasksFinder finder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        failed_adapter = new FailedTasksAdapter(getActivity(), failedTasksData);
        setListAdapter(failed_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        try
        {
            finder = new FailedTasksFinder();
            finder.execute(MainActivity.current_id);
            String failed_tasks = finder.get();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            FailedTaskJSON[] failedTaskJSON = gson.fromJson(failed_tasks, FailedTaskJSON[].class);
            for (int i = 0; i <= failedTaskJSON.length - 1; i++)
            {
                failedTasksData.add(new FailedTask(current_login, failedTaskJSON[i].text, failedTaskJSON[i].post_rating));
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.failed_tasks_fragment, container, false);
    }

    @Override
    public void onDestroyView()
    {
        failedTasksData.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        failedTasksData.clear();
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        failedTasksData.clear();
        super.onDetach();
    }

    private static class FailedTasksFinder extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/" + strings[0] +
                    "/faileds");
            return request.body();
        }
    }
}
