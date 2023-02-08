package com.example.lmssaraswaticollege;

import com.example.lmssaraswaticollege.books.BookService;
import com.example.lmssaraswaticollege.books.Books;
import com.example.lmssaraswaticollege.issue.Issue;
import com.example.lmssaraswaticollege.issue.IssueService;
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
    String[] bookDepartment = {"Select Department", "Jaydeep Sharda Nursing", "Saraswati mahavidyalaya", "Saraswati Vidyalaya"};

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
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel l1;
        JLabel l2;
        JLabel l3;

        l1 = new JLabel("Role");
        l1.setBounds(30, 10, 100, 30);

        l2 = new JLabel("Username");
        l2.setBounds(30, 60, 100, 30);

        l3 = new JLabel("Password");
        l3.setBounds(30, 110, 100, 30);

        JComboBox<String> roleSelection = new JComboBox<>(options);
        roleSelection.setBounds(110, 10, 200, 30);

        JTextField fUser = new JTextField();
        fUser.setBounds(110, 60, 200, 30);

        JPasswordField fPass = new JPasswordField();
        fPass.setBounds(110, 110, 200, 30);

        JButton loginBut = new JButton("Login");
        loginBut.setBounds(130, 185, 80, 25);

        loginBut.addActionListener(e -> {

            String username = fUser.getText();
            String password = String.valueOf(fPass.getPassword());
            String role = options[roleSelection.getSelectedIndex()];

            if (role.equals("Select Role")) {
                JOptionPane.showMessageDialog(null, "Please select a valid role");
            } else if (username.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter username");
            } else if (password.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter password");
            } else {
                Role roleSelected;
                if (role.equals(options[1]))
                    roleSelected = Role.ROLE_ADMIN;
                else if (role.equals(options[3]))
                    roleSelected = Role.ROLE_STUDENT;
                else
                    roleSelected = Role.ROLE_TEACHER;

                User user = new User(
                        username,
                        password,
                        roleSelected
                );

                if (userService.login(user)) {
                    f.dispose();
                    if (roleSelected.equals(Role.ROLE_ADMIN))
                        adminMenu();
                    else
                        userMenu();
                } else JOptionPane.showMessageDialog(null, "User does not exist");

            }
        });

        f.add(roleSelection);
        f.add(fPass); //add password
        f.add(loginBut);//adding button in JFrame
        f.add(fUser);  //add user
        f.add(l1);  // add label1 i.e. for role
        f.add(l2); // add label2 i.e. for username
        f.add(l3); //add label3 i.e. for password

        f.setSize(500, 300);
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setLocationRelativeTo(null);
    }

    public void adminMenu() {
        JFrame f = new JFrame("Admin Functions");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Complete
        JButton viewBut = new JButton("View Books");
        viewBut.setBounds(20, 20, 120, 25);
        viewBut.addActionListener(e -> new Form1(bookService, true));

        //Complete
        JButton usersBut = new JButton("View Users");
        usersBut.setBounds(150, 20, 120, 25);
        usersBut.addActionListener(e -> {
            JFrame f1 = new JFrame("Users List");

            JTable userList = new JTable();
            String[] columnNames = {"UID", "Username", "Password", "Role"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (User user : userService.getAllUsers()) {
                String id = user.getId().toString();
                String userName = user.getUserName();
                String password = user.getPassword();
                String role = user.getRole().name();

                model.addRow(new Object[]{id, userName, password, role});
            }

            userList.setModel(model);

            JScrollPane scrollPane = new JScrollPane(userList);

            f1.add(scrollPane); //add scrollpane
            f1.setSize(800, 400); //set size for frame
            f1.setVisible(true);
            f1.setLocationRelativeTo(null);

        });

        //Complete
        JButton addUser = new JButton("Add User");
        addUser.setBounds(20, 60, 120, 25);
        addUser.addActionListener(e -> {
            JFrame g = new JFrame("Enter User Details");

            JLabel l1;
            JLabel l2;
            JLabel l3;

            l1 = new JLabel("Role");
            l1.setBounds(30, 10, 100, 30);

            l2 = new JLabel("Username");  //Create label Username
            l2.setBounds(30, 60, 100, 30); //x axis, y axis, width, height

            l3 = new JLabel("Password");  //Create label Password
            l3.setBounds(30, 110, 100, 30);

            JComboBox<String> roleSelection = new JComboBox<>(options);
            roleSelection.setBounds(110, 10, 200, 30);

            JTextField fUser = new JTextField(); //Create text field for username
            fUser.setBounds(110, 60, 200, 30);

            JPasswordField fPass = new JPasswordField(); //Create text field for password
            fPass.setBounds(110, 110, 200, 30);

            JButton loginBut = new JButton("Login");//creating instance of JButton for Login Button
            loginBut.setBounds(130, 185, 80, 25);//Dimensions for button

            JButton createButton = new JButton("Add Uer");//creating instance of JButton for Create
            createButton.setBounds(130, 200, 80, 25);//x axis, y axis, width, height
            createButton.addActionListener(e1 -> {
                String username = fUser.getText();

                String password = "" + String.valueOf(fPass.getPassword());
                String role = options[roleSelection.getSelectedIndex()];

                Role roleSelected;
                if (role.equals("ADMIN"))
                    roleSelected = Role.ROLE_ADMIN;
                else if (role.equals("STUDENT"))
                    roleSelected = Role.ROLE_STUDENT;
                else
                    roleSelected = Role.ROLE_TEACHER;

                if (userService.saveUser(new User(username, password, roleSelected))) {
                    JOptionPane.showMessageDialog(null, "User Saved Successfully",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "User Already Exists",
                            "Save User", JOptionPane.ERROR_MESSAGE);

            });

            g.add(createButton);
            g.add(l1);
            g.add(l2);
            g.add(l3);
            g.add(roleSelection);
            g.add(fUser);
            g.add(fPass);
            g.setSize(400, 300);//400 width and 500 height
            g.setLayout(null);//using no layout managers
            g.setVisible(true);//making the frame visible
            g.setLocationRelativeTo(null);


        });

        //Complete
        JButton addBook = new JButton("Add Book");
        addBook.setBounds(150, 60, 120, 25);
        addBook.addActionListener(e -> {

            JFrame g = new JFrame("Add Book");

            addEditBook(g, "Add Book");
        });

        //Complete
        JButton issueBook = new JButton("Issue Book");
        issueBook.setBounds(280, 20, 160, 25);
        issueBook.addActionListener(e -> issueReturnBook("Issue Book"));

        //Complete
        JButton returnBook = new JButton("Return Book");
        returnBook.setBounds(280, 60, 160, 25);
        returnBook.addActionListener(e -> issueReturnBook("Return Book"));

        //Complete
        JButton editBook = new JButton("Edit Book");
        editBook.setBounds(100, 100, 160, 25);
        editBook.addActionListener(e -> {
            JFrame g = new JFrame("Edit Book");

            addEditBook(g, "Edit Book");
        });

        f.add(returnBook);
        f.add(issueBook);
        f.add(addBook);
        f.add(usersBut);
        f.add(viewBut);
        f.add(addUser);
        f.add(editBook);

        f.setSize(550, 200);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

    public void userMenu() {
        JFrame f = new JFrame("User Functions");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton viewBut = new JButton("View Books");
        viewBut.setBounds(20, 20, 120, 25);
        viewBut.addActionListener(e -> new Form1(bookService, false));

        f.add(viewBut);
        f.setSize(300, 100);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    //TODO: create dropdown for bookDepartment
    private void addEditBook(JFrame g, String action) {

        JLabel l1;
        JLabel l2;
        JLabel l3;
        JLabel l4;
        JLabel l5;
        JLabel l6;
        JLabel l7;
        JLabel l8;
        JLabel l9;

        l1 = new JLabel("Accession Id");
        l1.setBounds(30, 15, 125, 30);

        l2 = new JLabel("Book Name");
        l2.setBounds(30, 50, 125, 30);

        l3 = new JLabel("Department");
        l3.setBounds(30, 85, 125, 30);

        l4 = new JLabel("Author Name");
        l4.setBounds(30, 120, 125, 30);

        l9 = new JLabel("Publisher");
        l9.setBounds(30, 155, 125, 30);

        l5 = new JLabel("Year Published");
        l5.setBounds(30, 190, 125, 30);

        l6 = new JLabel("Pages");
        l6.setBounds(30, 225, 125, 30);

        l7 = new JLabel("Language");
        l7.setBounds(30, 260, 125, 30);

        l8 = new JLabel("Price");
        l8.setBounds(30, 295, 125, 30);

        JTextField fAcId = new JTextField();
        fAcId.setBounds(120, 15, 200, 30);

        JTextField fBook = new JTextField();
        fBook.setBounds(120, 50, 200, 30);

        JComboBox<String> fDept = new JComboBox<>(bookDepartment);
        fDept.setBounds(120, 85, 200, 30);

//        JTextField fDept = new JTextField();
//        fDept.setBounds(120, 85, 200, 30);

        JTextField fAuthor = new JTextField();
        fAuthor.setBounds(120, 120, 200, 30);

        JTextField fPublisher = new JTextField();
        fPublisher.setBounds(120, 155, 200, 30);

        JTextField fPublished = new JTextField();
        fPublished.setBounds(120, 190, 200, 30);

        JTextField fPages = new JTextField();
        fPages.setBounds(120, 225, 200, 30);

        JTextField fLanguage = new JTextField();
        fLanguage.setBounds(120, 260, 200, 30);

        JTextField fPrice = new JTextField();
        fPrice.setBounds(120, 295, 200, 30);

        JButton createBut = new JButton("Submit");
        createBut.setBounds(130, 330, 80, 25);
        createBut.addActionListener(e12 -> {

            String acId = fAcId.getText();
            String bookName = fBook.getText();
            String bookDept = bookDepartment[fDept.getSelectedIndex()];
            String author = fAuthor.getText();
            String published = fPublished.getText();
            String pages = fPages.getText();
            String lang = fLanguage.getText();
            String price = fPrice.getText();
            String publisher = fPublisher.getText();

            if ("Edit Book".equals(action)) {
                if (bookService.updateBook(new Books(acId, bookName, bookDept, author, publisher, published,
                        pages, lang, price, false))) {
                    JOptionPane.showMessageDialog(null, "Book Updated",
                            "Success message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                }
            } else {
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
            String accNo = fAcId.getText();
            String bookDept = bookDepartment[fDept.getSelectedIndex()];

            Books book = bookService.getBookByAccNoAndDept(accNo, bookDept);

            if (book != null) {
                fBook.setText(book.getBookName());
                fAuthor.setText(book.getAuthorName());
                fLanguage.setText(book.getLanguage());
                fPages.setText(String.valueOf(book.getNoOfPages()));
                fPrice.setText(String.valueOf(book.getPrice()));
                fPublished.setText(book.getYearOfPub());
                fPublisher.setText(book.getPublisher());
                createBut.setEnabled(true);
            } else
                JOptionPane.showMessageDialog(null, "Book Does Not Exist",
                        "Error Message", JOptionPane.ERROR_MESSAGE);

        });

        if (action.equals("Edit Book")) {
            createBut.setEnabled(false);
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

        g.add(fAcId);
        g.add(fDept);
        g.add(fBook);
        g.add(fAuthor);
        g.add(fPrice);
        g.add(fLanguage);
        g.add(fPages);
        g.add(fPublished);
        g.add(fPublisher);

        g.add(createBut);
        createBut.setVisible(true);

        g.setSize(600, 500);
        g.setLayout(null);
        g.setVisible(true);
        g.setLocationRelativeTo(null);
    }

    //Complete
    private void issueReturnBook(String action) {
        JFrame g = new JFrame("Enter Details");

        JLabel l1;
        JLabel l2;

        l1 = new JLabel("Accession ID");
        l1.setBounds(30, 15, 100, 30);

        l2 = new JLabel("Department");
        l2.setBounds(30, 80, 100, 30);

        JTextField fBid = new JTextField();
        fBid.setBounds(110, 15, 200, 30);

        JTextField fDept = new JTextField();
        fDept.setBounds(110, 80, 200, 30);

        JButton createBut;
        createBut = new JButton(action);
        createBut.setBounds(50, 170, 200, 25);

        if (action.equals("Issue Book")) {
            createBut.addActionListener(e13 -> {

                String bid = fBid.getText();
                String dept = fDept.getText();

                if (issueService.issueBook(new Issue(bid, dept))) {
                    JOptionPane.showMessageDialog(null, "Book Issued",
                            "Success Message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "The book is taken or not present",
                            "Error Message", JOptionPane.ERROR_MESSAGE);

            });
        } else {
            createBut.addActionListener(e14 -> {
                String acID = fBid.getText();
                String dept = fDept.getText();

                if (issueService.returnBook(acID, dept)) {
                    JOptionPane.showMessageDialog(null, "Book Returned",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "Book Not Issued",
                            "Message", JOptionPane.ERROR_MESSAGE);

            });
        }


        g.add(fBid);
        g.add(createBut);
        g.add(l1);
        g.add(fDept);
        g.add(l2);

        g.setSize(350, 350);
        g.setLayout(null);
        g.setVisible(true);
        g.setLocationRelativeTo(null);
    }

    //TODO: Complete method
    private void loginAddUser(String action) {

    }
}
