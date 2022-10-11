package it.itresources.springtut.springtutorial.mapper;

import it.itresources.springtut.springtutorial.entity.GradeEntity;
import it.itresources.springtut.springtutorial.model.dto.GradeDTO;
import it.itresources.springtut.springtutorial.model.request.NewGradeRequest;

public class GradeMapper {

    public static GradeDTO entityToDto (GradeEntity entity)
    {
        GradeDTO dto=new GradeDTO();
        dto.setId(entity.getId());
        dto.setGrade(entity.getGrade());
        dto.setStudent(entity.getStudent().getId());
        if (entity.getClassroom()!=null)
        {
            dto.setClassroom(entity.getClassroom().getId());
        } else {
            dto.setClassroom(null);
        }

        return dto;
    }

}
