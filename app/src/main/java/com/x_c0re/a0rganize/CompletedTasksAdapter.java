package com.x_c0re.a0rganize;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CompletedTasksAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    ArrayList<CompletedTask> сompletedTasks;

    GetJSONBodyOfUser user;

    ImageView mPostImage;
    ImageView mAvatar;

    CompletedTasksAdapter(Context context, ArrayList<CompletedTask> completedTasks)
    {
        this.context = context;
        this.сompletedTasks = completedTasks;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return сompletedTasks.size();
    }

    @Override
    public Object getItem(int position)
    {
        return сompletedTasks.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.completed_task_element, parent, false);
        }

        CompletedTask c = getCompletedTask(position);

        mPostImage = view.findViewById(R.id.imageViewCompletedImage);
        mAvatar = view.findViewById(R.id.avatarIconofCompletedElement);

        ((TextView)view.findViewById(R.id.textAuthorLogin_completed)).setText(c.author_login);
        ((TextView)view.findViewById(R.id.text_element_completed)).setText(c.text);
        ((TextView)view.findViewById(R.id.textViewCompletedRating)).setText(c.post_rating);
        Uri uri = Uri.parse("https://overcome-api.herokuapp.com" + c.proof_image);
        Glide.with(context).load(uri).into(mPostImage);

        try
        {
            user = new GetJSONBodyOfUser();
            user.execute(c.author_login);
            String body = user.get();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ContactJSON contactJSON = gson.fromJson(body, ContactJSON.class);
            String full_adress_to_avatar = "https://overcome-api.herokuapp.com" + contactJSON.avatar;
            Uri uri_to_avatar = Uri.parse(full_adress_to_avatar);
            Glide.with(context).load(uri_to_avatar).into(mAvatar);
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return view;
    }

    CompletedTask getCompletedTask(int position)
    {
        return (CompletedTask)getItem(position);
    }

    private static class GetJSONBodyOfUser extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/find_by_login/" + strings[0]);
            return request.body();
        }
    }
}
