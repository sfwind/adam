package com.dianping.ba.es.qyweixin.adapter.biz.entity;


import com.dianping.ba.es.qyweixin.adapter.biz.entity.media.TextDto;

/**
 * Created by justin on 9/23/14.
 * 文本消息格式
 */
public class TextMessageDto extends MessageDto{
    private TextDto textDto;

    public TextDto getTextDto() {
        return textDto;
    }

    public void setTextDto(TextDto textDto) {
        this.textDto = textDto;
        super.setMediaDto(textDto);
    }
}
