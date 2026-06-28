package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.domain.image.Image;
import com.notice.domain.user.User;
import com.notice.dto.elder.PostElderRequest;
import com.notice.dto.elder.PostElderResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.image.ImageRepository;
import com.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostElderService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ElderRepository elderRepository;

    @Transactional
    public PostElderResponse execute(Jwt jwt, PostElderRequest request) {
        Long userId = Long.valueOf(jwt.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HttpException.Unauthorized(
                        "token must be valid"
                ));

        Image image = getImageOrNull(request.getImageId());

        Elder elder = Elder.create(
                user,
                image,
                request.getName().trim(),
                request.getBirth(),
                request.getGender(),
                request.getWakeTime(),
                request.getSleepTime(),
                request.getUsesDiaper(),
                request.getBowelType(),
                request.getMobilityLevel(),
                request.getCognitiveStatus()
        );

        Elder savedElder = elderRepository.save(elder);

        return new PostElderResponse(savedElder.getId());
    }

    private Image getImageOrNull(Long imageId) {
        if (imageId == null) {
            return null;
        }

        return imageRepository.findById(imageId)
                .orElseThrow(() -> new HttpException.BadRequest(
                        "body[imageId] must be valid"
                ));
    }
}