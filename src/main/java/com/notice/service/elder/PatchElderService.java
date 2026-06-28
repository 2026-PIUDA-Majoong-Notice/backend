package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.domain.image.Image;
import com.notice.dto.elder.PatchElderRequest;
import com.notice.dto.elder.PatchElderResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatchElderService {

    private final ElderRepository elderRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public PatchElderResponse execute(Jwt jwt, Long elderId, PatchElderRequest request) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        updateElder(elder, request);

        return new PatchElderResponse(elder.getId());
    }

    private void updateElder(Elder elder, PatchElderRequest request) {
        if (request.getImageId() != null) {
            Image image = imageRepository.findById(request.getImageId())
                    .orElseThrow(() -> new HttpException.BadRequest(
                            "body[imageId] must be valid"
                    ));

            elder.updateImage(image);
        }

        if (request.getName() != null) {
            elder.updateName(request.getName().trim());
        }

        if (request.getBirth() != null) {
            elder.updateBirth(request.getBirth());
        }

        if (request.getGender() != null) {
            elder.updateGender(request.getGender());
        }

        if (request.getWakeTime() != null) {
            elder.updateWakeTime(request.getWakeTime());
        }

        if (request.getSleepTime() != null) {
            elder.updateSleepTime(request.getSleepTime());
        }

        if (request.getUsesDiaper() != null) {
            elder.updateUsesDiaper(request.getUsesDiaper());
        }

        if (request.getBowelType() != null) {
            elder.updateBowelType(request.getBowelType());
        }

        if (request.getMobilityLevel() != null) {
            elder.updateMobilityLevel(request.getMobilityLevel());
        }

        if (request.getCognitiveStatus() != null) {
            elder.updateCognitiveStatus(request.getCognitiveStatus());
        }
    }
}