package com.example.chriszhang.roomio.state;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chriszhang.roomio.activities.LoginActivity;
import com.example.chriszhang.roomio.activities.NotificationsActivity;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.JsonToObjectException;
import com.example.chriszhang.roomio.model.User;

import org.json.JSONException;
import org.json.JSONObject;


public class Client {

    private Context ctx;
    private String url = "http://172.20.29.173:7070";
    private RequestQueue rq;

    public Client(Context ctx){
        this.ctx = ctx;
        this.rq = Volley.newRequestQueue(ctx);
    }

    public void postLogin(final JSONObject loginObject, final View view) {
        JsonObjectRequest jr = new JsonObjectRequest(
                url + "/users/login", loginObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    System.out.println(response.toString(4));
                    Group group = Group.from(response);
                    User currentUser  =
                            group.getMemberFromUsername(
                                    loginObject.get("username").toString()).get();
                    State.createState(currentUser);
                    State.setGroup(group);
                    Intent intent = new Intent(ctx, NotificationsActivity.class);
                    ctx.startActivity(intent);
                } catch(JSONException | JsonToObjectException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view,
                        "Could not login: please check if your username or password is correct.",
                        Snackbar.LENGTH_LONG).show();
            }
        });
        rq.add(jr);
    }

    public void postSignup(final JSONObject signupObject, final View view) {
        JsonObjectRequest jr = new JsonObjectRequest(url + "/newusers", signupObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Snackbar.make(view, "Created new user!", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(ctx, LoginActivity.class);
                        ctx.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Snackbar.make(view, "Could not make user with specified fields. " +
                        "Either wait a while if this could be a connectivity issue or change username.",
                        Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(intent);
            }
        });

        rq.add(jr);
    }

    public void updateGroup(final JSONObject updateObject, final View view, final Intent transition) {
        JsonObjectRequest postReq = new JsonObjectRequest(url + "/groups", updateObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, "An error occurred with message: " + error.getMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
        });
        rq.add(postReq);

        String id = State.getGroup().getGroupId();
        JsonObjectRequest getReq = new JsonObjectRequest(url + "/groups/" + id, null,
                new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Group group = Group.from(response);
                    State.setGroup(group);
                    ctx.startActivity(transition);
                } catch(JSONException | JsonToObjectException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, "An error occurred with message: " + error.getMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
        });

        rq.add(getReq);
    }

}
