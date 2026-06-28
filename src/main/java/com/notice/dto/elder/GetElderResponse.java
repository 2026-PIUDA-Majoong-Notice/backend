package com.notice.dto.elder;

import com.notice.domain.elder.CognitiveStatus;
import com.notice.domain.elder.Elder;
import com.notice.domain.elder.MobilityLevel;
import com.notice.domain.image.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetElderResponse {

    private final List<ElderSummary> elders;

    public static GetElderResponse from(List<Elder> elders) {
        List<ElderSummary> summaries = elders.stream()
                .map(ElderSummary::from)
                .toList();

        return new GetElderResponse(summaries);
    }

    @Getter
    @RequiredArgsConstructor
    public static class ElderSummary {

        private final Long elderId;
        private final String name;
        private final Long imageId;
        private final String imageUrl;
        private final MobilityLevel mobilityLevel;
        private final CognitiveStatus cognitiveStatus;
        private final boolean usesDiaper;

        public static ElderSummary from(Elder elder) {
            Image image = elder.getImage();

            Long imageId = image == null ? null : image.getId();
            String imageUrl = image == null ? null : image.getImageUrl();

            return new ElderSummary(
                    elder.getId(),
                    elder.getName(),
                    imageId,
                    imageUrl,
                    elder.getMobilityLevel(),
                    elder.getCognitiveStatus(),
                    elder.isUsesDiaper()
            );
        }
    }
}