package com.x_c0re.a0rganize;

public class CompletedTask
{
    String author_login;
    String text;
    String proof_image;
    String post_rating;

    CompletedTask(String author_login, String text, String proof_image, String post_rating)
    {
        this.author_login = author_login;
        this.text = text;
        this.proof_image = proof_image;
        this.post_rating = post_rating;
    }
}
