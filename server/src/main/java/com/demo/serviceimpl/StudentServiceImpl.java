package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.StudentMapper;
import com.demo.pojo.StuClass;
import com.demo.pojo.Student;
import com.demo.service.StuClassService;
import com.demo.service.StudentService;
import com.demo.utils.POIUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StuClassService stuClassService;

    @Override
    public List<Student> findStudent(Student student) {
           QueryWrapper<Student> queryWrapper = new QueryWrapper<> ();
        queryWrapper.like (student.getName ()!=null,"name",student.getName ())
                .eq (null!=student.getClassId ()&&student.getClassId ()!=0,"class_id",student.getClassId ())
                .eq (null!=student.getStuNio ()&&student.getStuNio ()!=0,"stu_nio",student.getStuNio ());
        return studentMapper.selectList (queryWrapper);
    }

    @Override
    public List<Student> findByIds(List<Long> ids) {
        List<Student> students = studentMapper.selectBatchIds (ids);
        return students;
    }

    @Override
    public Student findByonid(Long id) {
        Student student = studentMapper.selectById (id);
        return student;
    }
    @Override
    public List<Student> stuNioPassword(String stuNio, String password){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq("stu_nio",stuNio).eq("password",password);
        List<Student> students = studentMapper.selectList(queryWrapper);
        return students;
    }

    @Override
    public String updateStudent(Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq ("stu_nio",student.getStuNio ());
        List<Student> list = studentMapper.selectList (queryWrapper);
        if(list.size ()>=1){
            if(list.get (0).getId () != student.getId ())
                return "error,学号重复";
        }
        int n = studentMapper.updateById (student);
        if(n == 0 ){
            return "error,修改失败";
        }
        return "success";
    }

    @Override
    public String insertStudent(Student student) {
        int n = studentMapper.insert (student);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String deleteStudent(Long id){
        int n = studentMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertForExcel(MultipartFile excelFile){      //excel导入学生信息，如果班级不存在则会自动创建
        try {
            List<String[]> list = POIUtils.readExcel (excelFile);
            list.removeIf (Objects::isNull);    //去掉null值
            for (int i = 0; i < list.size (); i++) {
                Iterator<String> iterator = Arrays.stream(list.get (i)).iterator ();
                while (iterator.hasNext ()){
                    if(iterator.next () == "")
                        iterator.remove ();
                }
            }
            if(list!=null&&(("姓名".equals (list.get (0)[0])||"班级".equals (list.get (0)[0]))||"学号".equals (list.get (0)[0])))//加一行表头判断
                list.remove (0);
            int n = 0;
            for(String[] ss : list){
                n++;
                for (int i = 0 ;i < ss.length;i++ ){
                    if(i == 0){
                        Long id = Long.valueOf (ss[i]);
                        Student stu = new Student();
                        stu.setStuNio(id);
                        List<Student> students = findStudent(stu);
     //学号查重
                        if(students.size() == 0 ){//将信息赋值给student
                            stu.setName (ss[1]);
                            List<StuClass> stuClasses= stuClassService.findByName (ss[2]);
                            if(stuClasses!=null){       //判断班级是否存在，不存在则创建
                                stu.setClassId (stuClasses.get (0).getId ());
                            }else {
                                StuClass stuClass = new StuClass ();
                                stuClass.setClassName (ss[2]);
                                stuClassService.insertStuClass (stuClass);
                                stu.setClassId (stuClass.getId ());
                            }
//                            student.setClassId (stuClassService.findByName (ss[0]).get (0).getId ());   //根据班级名字获得班级id
//                            System.out.println (student);
                            studentMapper.insert (stu);
                        }else{
                            String str = "error" + "第" + n + "行，数据学号"+ id +"与表中数据重复";
                            return str;
                        }

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace ();
        }
        return "success";
    }


}
