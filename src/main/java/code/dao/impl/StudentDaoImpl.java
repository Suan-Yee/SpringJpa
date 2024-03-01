package code.dao.impl;

import code.dao.StudentDao;
import code.entity.Student;
import code.utils.JPAUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Component
public class StudentDaoImpl implements StudentDao {

    @Override
    public Student createStudent(Student student) {
       EntityManager em = null;
       try{
           em = JPAUtil.getEntityManagerFactory().createEntityManager();
           em.getTransaction().begin();
           em.persist(student);
           em.getTransaction().commit();
       }finally {
           if(em != null && em.isOpen()){
               em.close();
           }
       }
       return student;
    }

    @Override
    public Student findById(Long studentId) {
        EntityManager em = null;
        Student student;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            student = em.find(Student.class,studentId);
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return student;
    }

    @Override
    public List<Student> findByName(String name) {
        EntityManager em = null;
        List<Student> students;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.name = :name ",Student.class);
            query.setParameter("name",name);
            students = query.getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return students;
    }

    @Override
    public List<Student> findAllStudent() {
        EntityManager em = null;
        List<Student> students;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            students = em.createQuery("SELECT s FROM Student s").getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return students;
    }

    @Override
    public Student updateStudent(Student student) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();

            Student updateStudent = em.find(Student.class,student.getId());

            if(updateStudent != null){
                updateStudent.setName(student.getName());
                updateStudent.setDob(student.getDob());
                updateStudent.setGender(student.getGender());
                updateStudent.setEducation(student.getEducation());
                updateStudent.setPhone(student.getPhone());
                updateStudent.setImageUrl(student.getImageUrl());
                Student updatedStudent = em.merge(updateStudent);
                em.getTransaction().commit();
                return updatedStudent;
            }else{
                em.getTransaction().rollback();
                return null;
            }
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }

    @Override
    public boolean deleteStudent(Long studentId) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Student student = em.find(Student.class,studentId);
            if (student != null) {
                em.remove(student);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
    private final Function<String,String> fileExtension = (fileName) -> Optional.of(fileName)
            .filter(name -> name.contains(".")).map(name -> "." + name.substring(fileName.lastIndexOf(".")+ 1)).orElse(".png");

    private final BiFunction<String, MultipartFile,String> photoFunctions = (id, image) -> {
        String fileName =  id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get(System.getProperty("user.home") + "/Downloads/uploads/").toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)) {Files.createDirectories(fileStorageLocation);}
            Files.copy(image.getInputStream(),fileStorageLocation.resolve(fileName),REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/contacts/image/" + fileName).toUriString();
        }catch(Exception e){
            throw new RuntimeException("Unable to save image");
        }
    };
}
