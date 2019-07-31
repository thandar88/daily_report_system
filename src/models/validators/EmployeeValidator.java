package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String code_error = _validateCode(e.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _validateName(e.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(e.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // �Ј��ԍ�
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        // �K�{���̓`�F�b�N
        if(code == null || code.equals("")) {
            return "�Ј��ԍ�����͂��Ă��������B";
        }

        // ���ɓo�^����Ă���Ј��ԍ��Ƃ̏d���`�F�b�N
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                           .setParameter("code", code)
                                             .getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "���͂��ꂽ�Ј��ԍ��̏��͊��ɑ��݂��Ă��܂��B";
            }
        }

        return "";
    }

    // �Ј����̕K�{���̓`�F�b�N
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "��������͂��Ă��������B";
        }

        return "";
    }

    // �p�X���[�h�̕K�{���̓`�F�b�N
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // �p�X���[�h��ύX����ꍇ�̂ݎ��s
        if(password_check_flag && (password == null || password.equals(""))) {
            return "�p�X���[�h����͂��Ă��������B";
        }
        return "";
    }
}