package my.vaadin.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;

public class CustomerForm extends CustomerFormDesign
{
    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;
    private Binder<Customer> binder = new Binder<>(Customer.class);

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;
        status.setItems(CustomerStatus.values());
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());

        // bind all named form fields to their counterparts in the Customer class
        // NOTE: If the naming convention based databinding doesn’t fit your needs, you can use PropertyId annotation on fields to explicitly declare the edited property.
        binder.bindInstanceFields(this);
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
        binder.setBean(customer);

        // show delete button only for customers already in the database
        delete.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() {
        service.delete(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save()
    {
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }
}
