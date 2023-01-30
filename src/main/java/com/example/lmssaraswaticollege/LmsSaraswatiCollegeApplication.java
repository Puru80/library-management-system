package com.example.lmssaraswaticollege;

import com.example.lmssaraswaticollege.books.BookService;
import com.example.lmssaraswaticollege.books.Books;
import com.example.lmssaraswaticollege.issue.Issue;
import com.example.lmssaraswaticollege.issue.IssueService;
import com.example.lmssaraswaticollege.sequence.SequenceRepository;
import com.example.lmssaraswaticollege.user.Role;
import com.example.lmssaraswaticollege.user.User;
import com.example.lmssaraswaticollege.user.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SpringBootApplication
public class LmsSaraswatiCollegeApplication extends JFrame {

    private final UserService userService;
    private final BookService bookService;
    private final IssueService issueService;

    String[] options = {"Select Role", "ADMIN", "TEACHER", "STUDENT"};

    public LmsSaraswatiCollegeApplication(UserService userService, BookService bookService, IssueService issueService) {
        this.userService = userService;
        this.bookService = bookService;
        this.issueService = issueService;
        login();
    }

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(LmsSaraswatiCollegeApplication.class);

        builder.headless(false).run(args);
    }

    public void login() {
        JFrame f = new JFrame("Login");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1, l2, l3;
        l1 = new JLabel("Role");
        l1.setBounds(30, 10, 100, 30);

        l2 = new JLabel("Username");
        l2.setBounds(30, 60, 100, 30);

        l3 = new JLabel("Password");
        l3.setBounds(30, 110, 100, 30);

        JComboBox<String> roleSelection = new JComboBox<>(options);
        roleSelection.setBounds(110, 10, 200, 30);

        JTextField F_user = new JTextField();
        F_user.setBounds(110, 60, 200, 30);

        JPasswordField F_pass = new JPasswordField();
        F_pass.setBounds(110, 110, 200, 30);

        JButton login_but = new JButton("Login");
        login_but.setBounds(130, 185, 80, 25);

        login_but.addActionListener(e -> {

            String username = F_user.getText();
            String password = String.valueOf(F_pass.getPassword());
            String role = options[roleSelection.getSelectedIndex()];

            if (role.equals("Select Role")) {
                JOptionPane.showMessageDialog(null, "Please select a valid role");
            } else if (username.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter username");
            } else if (password.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter password");
            }
            else {
                Role roleSelected;
                if (role.equals("ADMIN"))
                    roleSelected = Role.ROLE_ADMIN;
                else if (role.equals("STUDENT"))
                    roleSelected = Role.ROLE_STUDENT;
                else
                    roleSelected = Role.ROLE_TEACHER;

                User user = new User(
                        username,
                        password,
                        roleSelected
                );

                if(userService.login(user)){
                    f.dispose();
                    if(roleSelected.equals(Role.ROLE_ADMIN))
                        admin_menu();
                    else
                        user_menu();
                }
                else JOptionPane.showMessageDialog(null, "User does not exist");

            }
        });

        setUpLogin(f, l1, l2, l3, roleSelection, F_user, F_pass, login_but);
    }

    public void setUpLogin(JFrame f, JLabel l1, JLabel l2, JLabel l3, JComboBox<String> roleSelection,
                           JTextField f_user, JPasswordField f_pass, JButton login_but) {
        f.add(roleSelection);
        f.add(f_pass); //add password
        f.add(login_but);//adding button in JFrame
        f.add(f_user);  //add user
        f.add(l1);  // add label1 i.e. for role
        f.add(l2); // add label2 i.e. for username
        f.add(l3); //add label3 i.e. for password

        f.setSize(500, 300);
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setLocationRelativeTo(null);
    }

    public void admin_menu() {
        JFrame f = new JFrame("Admin Functions");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Complete
        JButton view_but=new JButton("View Books");
        view_but.setBounds(20,20,120,25);
        view_but.addActionListener(e -> new Form1(bookService, true));

        //Complete
        JButton users_but = new JButton("View Users");
        users_but.setBounds(150,20,120,25);
        users_but.addActionListener(e -> {
            JFrame f1 = new JFrame("Users List");

            JTable user_list= new JTable();
            String[] columnNames = {"UID", "Username", "Password", "Role"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for(User user : userService.getAllUsers()){
                String id = user.getId().toString();
                String userName = user.getUserName();
                String password = user.getPassword();
                String role = user.getRole().name();

                model.addRow(new Object[]{id, userName, password, role});
            }

            user_list.setModel(model);

            JScrollPane scrollPane = new JScrollPane(user_list);

            f1.add(scrollPane); //add scrollpane
            f1.setSize(800, 400); //set size for frame
            f1.setVisible(true);
            f1.setLocationRelativeTo(null);

        });

        //Complete
        JButton add_user = new JButton("Add User");
        add_user.setBounds(20,60,120,25);
        add_user.addActionListener(e -> {
            JFrame g = new JFrame("Enter User Details");

            JLabel l1, l2, l3;
            l1 = new JLabel("Role");
            l1.setBounds(30, 10, 100, 30);

            l2 = new JLabel("Username");  //Create label Username
            l2.setBounds(30, 60, 100, 30); //x axis, y axis, width, height

            l3 = new JLabel("Password");  //Create label Password
            l3.setBounds(30, 110, 100, 30);

            JComboBox<String> roleSelection = new JComboBox<>(options);
            roleSelection.setBounds(110, 10, 200, 30);

            JTextField F_user = new JTextField(); //Create text field for username
            F_user.setBounds(110, 60, 200, 30);

            JPasswordField F_pass = new JPasswordField(); //Create text field for password
            F_pass.setBounds(110, 110, 200, 30);

            JButton login_but = new JButton("Login");//creating instance of JButton for Login Button
            login_but.setBounds(130, 185, 80, 25);//Dimensions for button

            JButton create_button = new JButton("Add Uer");//creating instance of JButton for Create
            create_button.setBounds(130,200,80,25);//x axis, y axis, width, height
            create_button.addActionListener(e1 -> {
                String username = F_user.getText();

                String password = "" + String.valueOf(F_pass.getPassword());
                String role = options[roleSelection.getSelectedIndex()];

                Role roleSelected;
                if (role.equals("ADMIN"))
                    roleSelected = Role.ROLE_ADMIN;
                else if (role.equals("STUDENT"))
                    roleSelected = Role.ROLE_STUDENT;
                else
                    roleSelected = Role.ROLE_TEACHER;

                if(userService.saveUser(new User(username, password, roleSelected))){
                    JOptionPane.showMessageDialog(null, "User Saved Successfully",
                            "Message",JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "User Already Exists",
                            "Save User", JOptionPane.ERROR_MESSAGE);

            });

            g.add(create_button);
            g.add(l1);
            g.add(l2);
            g.add(l3);
            g.add(roleSelection);
            g.add(F_user);
            g.add(F_pass);
            g.setSize(400,300);//400 width and 500 height
            g.setLayout(null);//using no layout managers
            g.setVisible(true);//making the frame visible
            g.setLocationRelativeTo(null);


        });

        //Complete
        JButton add_book=new JButton("Add Book");
        add_book.setBounds(150,60,120,25);
        add_book.addActionListener(e -> {

            JFrame g = new JFrame("Add Book");

            addEditBook(g, "Add Book");
        });

        //Complete
        JButton issue_book=new JButton("Issue Book");
        issue_book.setBounds(280,20,160,25);
        issue_book.addActionListener(e -> issueReturnBook("Issue Book"));

        //Complete
        JButton return_book = new JButton("Return Book");
        return_book.setBounds(280,60,160,25);
        return_book.addActionListener(e -> issueReturnBook("Return Book"));

        //Complete
        JButton editBook = new JButton("Edit Book");
        editBook.setBounds(100, 100, 160, 25);
        editBook.addActionListener(e -> {
            JFrame g = new JFrame("Edit Book");

            addEditBook(g, "Edit Book");
        });

        f.add(return_book);
        f.add(issue_book);
        f.add(add_book);
        f.add(users_but);
        f.add(view_but);
        f.add(add_user);
        f.add(editBook);

        f.setSize(550,200);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

    public void user_menu() {
        JFrame f=new JFrame("User Functions");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton view_but=new JButton("View Books");
        view_but.setBounds(20,20,120,25);
        view_but.addActionListener(e -> new Form1(bookService, false));

        f.add(view_but);
        f.setSize(300,100);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    //Complete
    private void addEditBook(JFrame g, String action){

        JLabel l1,l2,l3, l4, l5, l6, l7, l8, l9;
        l1=new JLabel("Accession Id");
        l1.setBounds(30,15, 125,30);

        l2 = new JLabel("Book Name");
        l2.setBounds(30,50, 125,30);

        l3 = new JLabel("Department");
        l3.setBounds(30, 85, 125, 30);

        l4 = new JLabel("Author Name");
        l4.setBounds(30,120, 125,30);

        l9 = new JLabel("Publisher");
        l9.setBounds(30,155, 125,30);

        l5 = new JLabel("Year Published");
        l5.setBounds(30,190, 125,30);

        l6 = new JLabel("Pages");
        l6.setBounds(30,225, 125,30);

        l7 = new JLabel("Language");
        l7.setBounds(30,260, 125,30);

        l8 = new JLabel("Price");
        l8.setBounds(30,295, 125,30);

        JTextField F_acId = new JTextField();
        F_acId.setBounds(120, 15, 200, 30);

        JTextField F_book =new JTextField();
        F_book.setBounds(120, 50, 200, 30);

        JTextField F_dept = new JTextField();
        F_dept.setBounds(120, 85, 200, 30);

        JTextField F_author =new JTextField();
        F_author.setBounds(120,120, 200,30);

        JTextField F_publisher = new JTextField();
        F_publisher.setBounds(120,155, 200,30);

        JTextField F_published =new JTextField();
        F_published.setBounds(120, 190, 200, 30);

        JTextField F_pages =new JTextField();
        F_pages.setBounds(120, 225, 200, 30);

        JTextField F_language =new JTextField();
        F_language.setBounds(120, 260, 200, 30);

        JTextField F_price =new JTextField();
        F_price.setBounds(120, 295, 200, 30);

        JButton create_but=new JButton("Submit");
        create_but.setBounds(130,330,80,25);
        create_but.addActionListener(e12 -> {

            String acId = F_acId.getText();
            String bookName = F_book.getText();
            String bookDept = F_dept.getText();
            String author = F_author.getText();
            String published = F_published.getText();
            String pages = F_pages.getText();
            String lang = F_language.getText();
            String price = F_price.getText();
            String publisher = F_publisher.getText();

            if(action.equals("Edit Book")){
                if(bookService.updateBook(new Books(acId, bookName, bookDept, author, publisher, published,
                        pages, lang, price, false))) {
                    JOptionPane.showMessageDialog(null, "Book Updated",
                            "Success message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                }
            }
            else {
                if (bookService.addBook(new Books(acId, bookName, bookDept, author, publisher, published,
                        pages, lang, price, false))) {
                    JOptionPane.showMessageDialog(null, "Book Added to Database",
                            "Success message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "Book Already Exists",
                            "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton getBookDeets = new JButton("Get Details");
        getBookDeets.setBounds(350, 15, 100, 30);
        getBookDeets.addActionListener(e -> {
            String accNo = F_acId.getText();
            String department = F_dept.getText();

            Books book = bookService.getBookByAccNoAndDept(accNo, department);

            if(book != null) {
                F_book.setText(book.getBookName());
                F_author.setText(book.getAuthorName());
                F_language.setText(book.getLanguage());
                F_pages.setText(String.valueOf(book.getNoOfPages()));
                F_price.setText(String.valueOf(book.getPrice()));
                F_published.setText(book.getYearOfPub());
                F_publisher.setText(book.getPublisher());
                create_but.setEnabled(true);
            }
            else
                JOptionPane.showMessageDialog(null, "Book Does Not Exist",
                        "Error Message", JOptionPane.ERROR_MESSAGE);

        });

        if(action.equals("Edit Book")){
            create_but.setEnabled(false);
            g.add(getBookDeets);
        }

        g.add(l1);
        g.add(l2);
        g.add(l3);
        g.add(l4);
        g.add(l5);
        g.add(l6);
        g.add(l7);
        g.add(l8);
        g.add(l9);

        g.add(F_acId);
        g.add(F_dept);
        g.add(F_book);
        g.add(F_author);
        g.add(F_price);
        g.add(F_language);
        g.add(F_pages);
        g.add(F_published);
        g.add(F_publisher);

        g.add(create_but);
        create_but.setVisible(true);

        g.setSize(600,500);
        g.setLayout(null);
        g.setVisible(true);
        g.setLocationRelativeTo(null);
    }

    //Complete
    private void issueReturnBook(String action){
        JFrame g = new JFrame("Enter Details");

        JLabel l1, l2;
        l1 = new JLabel("Accession ID");
        l1.setBounds(30,15, 100,30);

        l2 = new JLabel("Department");
        l2.setBounds(30, 80, 100, 30);

        JTextField F_bid = new JTextField();
        F_bid.setBounds(110, 15, 200, 30);

        JTextField F_dept = new JTextField();
        F_dept.setBounds(110, 80, 200, 30);

        JButton create_but;
        create_but = new JButton(action);
        create_but.setBounds(50,170,200,25);

        if(action.equals("Issue Book")) {
            create_but.addActionListener(e13 -> {

                String bid = F_bid.getText();
                String dept = F_dept.getText();

                if (issueService.issueBook(new Issue(bid, dept))) {
                    JOptionPane.showMessageDialog(null, "Book Issued",
                            "Success Message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "The book is taken or not present",
                            "Error Message", JOptionPane.ERROR_MESSAGE);

            });
        }
        else{
            create_but.addActionListener(e14 -> {
                String acID = F_bid.getText();
                String dept = F_dept.getText();

                if(issueService.returnBook(acID, dept)) {
                    JOptionPane.showMessageDialog(null, "Book Returned",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "Book Not Issued",
                            "Message", JOptionPane.ERROR_MESSAGE);

            });
        }


        g.add(F_bid);
        g.add(create_but);
        g.add(l1);
        g.add(F_dept);
        g.add(l2);

        g.setSize(350,350);
        g.setLayout(null);
        g.setVisible(true);
        g.setLocationRelativeTo(null);
    }

    //TODO: Complete method
    private void loginAddUser(String action){

    }
}
