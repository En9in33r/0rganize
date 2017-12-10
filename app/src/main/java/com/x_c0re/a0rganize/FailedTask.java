package com.x_c0re.a0rganize;

public class FailedTask
{
    String author_login;
    String text;
    float post_rating;

    FailedTask(String author_login, String text, float post_rating)
    {
        this.author_login = author_login;
        this.text = text;
        this.post_rating = post_rating;
    }
}
