package com.friends.easybud.global.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SlackService {

    @Value(value = "${spring.profiles.active}")
    String profile;
    @Value(value = "${slack.token:}")
    String token;
    @Value(value = "${slack.channel.monitor:}")
    String channelProductError;

    private static final String LOCAL = "local";
    private static final String PROD_ERROR_MESSAGE_TITLE = "🤯 *500 에러 발생*";
    private static final String ATTACHMENTS_ERROR_COLOR = "#eb4034";

    public void sendSlackMessageProductError(Exception exception) {
        if (!profile.equals(LOCAL)) {
            try {
                List<LayoutBlock> layoutBlocks = SlackServiceUtils.createProdErrorMessage(exception);
                List<Attachment> attachments = SlackServiceUtils.createAttachments(ATTACHMENTS_ERROR_COLOR,
                        layoutBlocks);
                Slack.getInstance().methods(token).chatPostMessage(request ->
                        request.channel(channelProductError)
                                .attachments(attachments)
                                .text(PROD_ERROR_MESSAGE_TITLE));
            } catch (SlackApiException | IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}