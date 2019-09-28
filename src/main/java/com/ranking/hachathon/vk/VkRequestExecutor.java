package com.ranking.hachathon.vk;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.wall.WallGetQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VkRequestExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(VkRequestExecutor.class);

    private static final Integer APP_ID = 7151401;
    private static final String ACCESS_TOKEN = "6f56bd416f56bd416f56bd412b6f3ba26866f566f56bd4132d9f1c077161680c2ff8f10";
    private static final String CLIENT_SECRET = "ZDurOdNGKw1bpP9c6Mo7";

    public List<VkUser> makeGetUsersRequest(Set<String> ids) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient, new Gson(), 3);
        ServiceActor actor = new ServiceActor(APP_ID, CLIENT_SECRET, ACCESS_TOKEN);

        GetResponse response = null;
        try {
            response = vk.wall().get(actor).ownerId(16186533).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        List<WallpostFull> items = response.getItems();

        return Collections.emptyList();
    }

    public List<VkPost> getPosts(Integer vkUserId) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient, new Gson(), 3);
        ServiceActor actor = new ServiceActor(APP_ID, CLIENT_SECRET, ACCESS_TOKEN);

        try {
            WallGetQuery wallGetQuery = vk.wall().get(actor).ownerId(vkUserId);

            // Get 20 latest wall posts
            GetResponse response = wallGetQuery.execute();

            return response.getItems().stream()
                    .map(wallpostFull -> {
                        VkPost vkPost = new VkPost();
                        vkPost.setPostId(wallpostFull.getId());
                        vkPost.setText(wallpostFull.getText());
                        vkPost.setLikeCount(wallpostFull.getLikes().getCount());
                        vkPost.setRepostCount(wallpostFull.getReposts().getCount());
                        vkPost.setViewCount(wallpostFull.getViews().getCount());
                        vkPost.setCommentCount(wallpostFull.getComments().getCount());
                        vkPost.setCreatedDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(wallpostFull.getDate()), ZoneId.systemDefault()));

                        List<VkContent> contents = wallpostFull.getAttachments().stream().map(wallpostAttachment -> {
                            VkContent content = new VkContent();
                            if (wallpostAttachment.getType() == WallpostAttachmentType.PHOTO) {
                                content.setType(VkPostType.PHOTO);
                                content.setHeight(wallpostAttachment.getPhoto().getSizes().get(2).getHeight());
                                content.setWidth(wallpostAttachment.getPhoto().getSizes().get(2).getWidth());
                                content.setUrl(wallpostAttachment.getPhoto().getSizes().get(2).getUrl().toString());
                            } else if (wallpostAttachment.getType() == WallpostAttachmentType.VIDEO) {
                                content.setType(VkPostType.VIDEO);
                                content.setHeight(wallpostAttachment.getVideo().getHeight());
                                content.setWidth(wallpostAttachment.getVideo().getWidth());
                            }

                            return content;
                        }).collect(Collectors.toList());
                        vkPost.setContent(contents);

                        // Comments
                        // vk.wall().getComments(actor).ownerId(32707600).postId(wallpostFull.getId()).count(221).execute()

                        return vkPost;
                    }).collect(Collectors.toList());
        } catch (ApiException | ClientException e) {
            LOGGER.error("An exception occurred during getPosts", e);
            return Collections.emptyList();
        }
    }

}
