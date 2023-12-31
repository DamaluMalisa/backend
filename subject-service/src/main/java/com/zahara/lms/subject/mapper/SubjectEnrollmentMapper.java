package com.zahara.lms.subject.mapper;

import com.zahara.lms.shared.mapper.BaseMapper;
import com.zahara.lms.subject.dto.StudentDTO;
import com.zahara.lms.subject.dto.SubjectDTO;
import com.zahara.lms.subject.dto.SubjectEnrollmentDTO;
import com.zahara.lms.subject.dto.TeacherDTO;
import com.zahara.lms.subject.model.Subject;
import com.zahara.lms.subject.model.SubjectEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectEnrollmentMapper
        extends BaseMapper<SubjectEnrollment, SubjectEnrollmentDTO, Long> {
    @Mapping(source = "studentId", target = "student")
    SubjectEnrollmentDTO toDTO(SubjectEnrollment subjectEnrollment);

    @Mapping(source = "student.id", target = "studentId")
    SubjectEnrollment toModel(SubjectEnrollmentDTO subjectEnrollmentDTO);

    StudentDTO studentDTOFromId(Long id);

    @Mapping(target = "studyProgram", ignore = true)
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "assistantId", target = "assistant")
    SubjectDTO toDTO(Subject subject);

    TeacherDTO teacherDTOFromId(Long id);
}
