package ru.kavcoffeefox.kcftaskmanager.service.impl;

import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.dao.DepartmentDAO;
import ru.kavcoffeefox.kcftaskmanager.dao.DepartmentDAOHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.entity.Department;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;

import java.util.List;

public class DepartmentManagerHibernateImpl extends AbstractManager implements Manager<Department, Integer> {

    private volatile static DepartmentManagerHibernateImpl INSTANCE = null;
    private DepartmentDAO departmentDAO;

    private DepartmentManagerHibernateImpl() {
        departmentDAO = new DepartmentDAOHibernateImpl();
    }

    @Override
    public boolean add(Department item) {
        return departmentDAO.add(item);
    }

    @Override
    public Department get(Integer id) {
        return departmentDAO.get(id);
    }

    @Override
    public boolean delete(Integer id) {
        return departmentDAO.delete(id);
    }

    @Override
    public boolean update(Integer id, Department item) {
        return departmentDAO.update(id, item);
    }

    @Override
    public List<Department> getAll() {
        return departmentDAO.getAll();
    }

    @Override
    protected boolean showView(Stage stage, SimpleItem item) {
        return showModalView(stage, "/view/modalwindows/DepartmentView.fxml", item);
    }

    public Department showDepartmentView(Stage stage){
        Department department = new Department();
        boolean isOk = showView(stage, department);
        if (isOk) {
            departmentDAO.add(department);
            return department;
        }
        else {
            return null;
        }
    }

    public static DepartmentManagerHibernateImpl getInstance() {
        if (INSTANCE == null)
            synchronized (DepartmentManagerHibernateImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DepartmentManagerHibernateImpl();
                }
            }
        return INSTANCE;
    }

}
