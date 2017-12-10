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

public class CurrentTasksFragment extends ListFragment
{
    public static ArrayList<CurrentTask> data = new ArrayList<>();
    CurrentTasksAdapter adapter;

    public static String current_login;
    RunningTasksFinder finder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        adapter = new CurrentTasksAdapter(getActivity(), data);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // здесь идет добавление элементов
        try
        {
            finder = new RunningTasksFinder();
            finder.execute(MainActivity.current_id);
            String running_tasks = finder.get();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CurrentTaskJSON[] currentTaskJSON = gson.fromJson(running_tasks, CurrentTaskJSON[].class);
            for (int i = 0; i <= currentTaskJSON.length - 1; i++)
            {
                data.add(new CurrentTask(current_login, currentTaskJSON[i].text));
            }
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.current_tasks_fragment, container, false);
    }

    @Override
    public void onDestroyView()
    {
        data.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        data.clear();
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        data.clear();
        super.onDetach();
    }

    private static class RunningTasksFinder extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/" + strings[0] +
            "/runnings");
            return request.body();
        }
    }
}