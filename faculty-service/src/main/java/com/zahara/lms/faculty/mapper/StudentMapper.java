package com.zahara.lms.faculty.mapper;

import com.zahara.lms.faculty.dto.StudentDTO;
import com.zahara.lms.faculty.dto.StudyProgramDTO;
import com.zahara.lms.faculty.dto.ThesisDTO;
import com.zahara.lms.faculty.client.SubjectFeignClient;
import com.zahara.lms.faculty.model.Student;
import com.zahara.lms.faculty.model.StudyProgram;
import com.zahara.lms.faculty.model.Thesis;
import com.zahara.lms.shared.dto.UserDTO;
import com.zahara.lms.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StudentMapper implements BaseMapper<Student, StudentDTO, Long> {
    private SubjectFeignClient subjectFeignClient;

    @Autowired
    public void setSubjectFeignClient(SubjectFeignClient subjectFeignClient) {
        this.subjectFeignClient = subjectFeignClient;
    }

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "id", target = "averageGrade", qualifiedByName = "getAverageGrade")
    @Mapping(source = "id", target = "totalECTS", qualifiedByName = "getTotalECTS")
    public abstract StudentDTO toDTO(Student student);

    @Mapping(source = "user.id", target = "userId")
    public abstract Student toModel(StudentDTO studentDTO);

    public List<StudentDTO> toDTO(List<Student> students) {
        List<StudentDTO> list = students.stream().map(this::toDTO).toList();
        if (!list.isEmpty()) {
            List<Long> studentIds = list.stream().map(StudentDTO::getId).toList();
            List<Double> averageGrades = subjectFeignClient.getAverageGradesByStudentId(studentIds);
            List<Integer> totalECTS = subjectFeignClient.getTotalECTSByStudentId(studentIds);

            for (int i = 0; i < list.size(); i++) {
                list.get(i).setAverageGrade(averageGrades.get(i));
                list.get(i).setTotalECTS(totalECTS.get(i));
            }
        }

        return list;
    }

    public abstract UserDTO userDTOFromId(Long id);

    @Mapping(target = "student", ignore = true)
    public abstract ThesisDTO toDTO(Thesis thesis);

    @Mapping(target = "faculty", ignore = true)
    @Mapping(target = "manager", ignore = true)
    public abstract StudyProgramDTO toDTO(StudyProgram studyProgram);

    @Named("getAverageGrade")
    public Double getAverageGrade(Long studentId) {
        return subjectFeignClient.getAverageGradesByStudentId(List.of(studentId)).get(0);
    }

    @Named("getTotalECTS")
    public Integer getTotalECTS(Long studentId) {
        return subjectFeignClient.getTotalECTSByStudentId(List.of(studentId)).get(0);
    }
}
