package com.notice.domain.log;

import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "medication_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_id", nullable = false)
    private Elder elder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_medication_id", nullable = false)
    private ElderMedication elderMedication;

    @Column(nullable = false)
    private LocalDate logDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MedicationTimeSlot timeSlot;

    @Column(nullable = false)
    private boolean taken;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private MedicationLog(
            Elder elder,
            ElderMedication elderMedication,
            LocalDate logDate,
            MedicationTimeSlot timeSlot,
            boolean taken
    ) {
        this.elder = elder;
        this.elderMedication = elderMedication;
        this.logDate = logDate;
        this.timeSlot = timeSlot;
        this.taken = taken;
        this.createdAt = LocalDateTime.now();
    }

    public static MedicationLog create(
            Elder elder,
            ElderMedication elderMedication,
            LocalDate logDate,
            MedicationTimeSlot timeSlot,
            boolean taken
    ) {
        return new MedicationLog(
                elder,
                elderMedication,
                logDate,
                timeSlot,
                taken
        );
    }
}