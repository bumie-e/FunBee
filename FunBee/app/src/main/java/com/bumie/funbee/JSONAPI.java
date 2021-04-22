package com.bumie.funbee;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSONAPI {
    @GET("/v0.2/users")
    Call<java.util.List<Model>> getUsers();
    @GET("/v0.1/users/{user_id}")
    Call<java.util.List<Model>> getUser(@Path("user_id") String user_id);

    @GET("/v0.2/conversations")
    Call<java.util.List<Model>> getConversations();
    @GET("/v0.2/conversations/{conversation_id}")
    Call<java.util.List<Model>> getAConversation(@Path("conversation_id") String conversation_id);

    @GET("/v0.2/conversations/{conversation_id}/members}")
    Call<java.util.List<Model>> getMembers(@Path("conversation_id") String conversation_id);
    @GET("predict/{sentence}")
    Call<Model> getPrediction(@Path("sentence") String sentence);

    @PUT("/v0.1/users/{user_id}")
    Call<Model> updateUser(@Header("Authorization") String jwt,
                           @Path("user_id") String user_id,
                           @Field("name") String name,
                           @Field("display_name") String display_name,
                           @Field("image_url") String image_url
    );


    @POST("/v0.1/users")
    Call<Model> createUser(@Header("Authorization") String jwt,
                           @Field("name") String name,
                           @Field("display_name") String display_name,
                           @Field("image_url") String image_url
    );
    @POST()
    Call<Model> createConversation(@Header("Authorization") String jwt,
                                   @Field("name") String name,
                                   @Field("display_name") String display_name,
                                   @Field("image_url") String image_url
    );
    @POST("/v0.2/conversations/{conversation_id}/members")
    Call<Model> createMember(
            @Header("Authorization") String jwt,
            @Path("conversation_id") String conversation_id,
            @Field("user_id") String user_id
    );
}
