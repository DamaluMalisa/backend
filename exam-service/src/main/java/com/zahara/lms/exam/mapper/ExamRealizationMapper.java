package com.zahara.lms.exam.mapper;

import com.zahara.lms.exam.dto.ExamRealizationDTO;
import com.zahara.lms.exam.dto.SubjectEnrollmentDTO;
import com.zahara.lms.exam.model.ExamRealization;
import com.zahara.lms.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class ExamRealizationMapper implements BaseMapper<ExamRealization, ExamRealizationDTO, Long> {
    @Mapping(source = "subjectEnrollmentId", target = "subjectEnrollment")
    @Mapping(source = "examRealization", target = "passed", qualifiedByName = "hasPassed")
    public abstract ExamRealizationDTO toDTO(ExamRealization examRealization);

    @Mapping(source = "subjectEnrollment.id", target = "subjectEnrollmentId")
    public abstract ExamRealization toModel(ExamRealizationDTO examRealizationDTO);

    public abstract SubjectEnrollmentDTO subjectEnrollmentDTOFromId(Long id);

    @Named("hasPassed")
    public Boolean hasPassed(ExamRealization examRealization) {
        Integer score = examRealization.getScore();
        return score != null
                ? score >= examRealization.getExamTerm().getExam().getMinimumScore()
                : null;
    }
}
