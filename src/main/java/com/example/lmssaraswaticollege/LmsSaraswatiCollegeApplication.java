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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        new SpringApplicationBuilder(LmsSaraswatiCollegeApplication.class)
                .headless(false).run(args);
    }

    /*@Bean
    CommandLineRunner runner(UserService userService){
        return args -> {
            User user = new User(
                    "Admin",
                    "admin",
                    Role.ROLE_ADMIN
            );

            userService.saveUser(user);
        };
    }*/

    public void login() {
        JFrame f = new JFrame("Login");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

                if(userService.login(user) && roleSelected.equals(Role.ROLE_ADMIN)){
                    f.dispose();
                    admin_menu();
                }
                //TODO:  Condition for user menu
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
        view_but.addActionListener(e -> {

                JFrame f1 = new JFrame("Books Available");

                JTable book_list= new JTable();
                String[] columnNames = {"Accession No", "Name", "Author", "Published In", "Abscission No",
                        "Language", "Price"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                for(Books books: bookService.getAllBooks()){
                    String AccNo = books.getAccNo();
                    String name = books.getBookName();
                    String author = books.getAuthorName();
                    String publishedIn = books.getYearOfPub();
                    String abNo = books.getAbscissionNo();
                    String language = books.getLanguage();
                    Double price = books.getPrice();

                    model.addRow(new Object[]{AccNo, name, author, publishedIn, abNo, language, price});
                }

                book_list.setModel(model);

                JScrollPane scrollPane = new JScrollPane(book_list);

                f1.add(scrollPane);
                f1.setSize(800, 400); //set size for frame
                f1.setVisible(true);
                f1.setLocationRelativeTo(null);

            });

        JButton issued_but = new JButton("View Issued Books");
        issued_but.setBounds(280,20,160,25);
        issued_but.addActionListener(e -> {

            JFrame f13 = new JFrame("Users List");

            /*Connection connection = connect();
            String sql="select * from issued";
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("USE LIBRARY");
                stmt=connection.createStatement();
                ResultSet rs=stmt.executeQuery(sql);
                JTable book_list= new JTable();
                book_list.setModel(DbUtils.resultSetToTableModel(rs));

                JScrollPane scrollPane = new JScrollPane(book_list);

                f.add(scrollPane);
                f.setSize(800, 400);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, e1);
            }*/

        });

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

            JFrame g = new JFrame("Enter Book Details");

            JLabel l1,l2,l3, l4, l5, l6, l7, l8;
            l1=new JLabel("Accession Id");
            l1.setBounds(30,15, 115,30);

            l2 = new JLabel("Book Name");
            l2.setBounds(30,50, 115,30);

            l3 = new JLabel("Author Name");
            l3.setBounds(30, 85, 115, 30);

            l4 = new JLabel("Year Published");
            l4.setBounds(30,120, 115,30);

            l5 = new JLabel("Abscission Id");
            l5.setBounds(30,155, 115,30);

            l6 = new JLabel("Pages");
            l6.setBounds(30,190, 115,30);

            l7 = new JLabel("Language");
            l7.setBounds(30,225, 115,30);

            l8 = new JLabel("Price");
            l8.setBounds(30,260, 115,30);

            JTextField F_acId = new JTextField();
            F_acId.setBounds(110, 15, 200, 30);

            JTextField F_book =new JTextField();
            F_book.setBounds(110, 50, 200, 30);

            JTextField F_author =new JTextField();
            F_author.setBounds(110, 85, 200, 30);

            JTextField F_published =new JTextField();
            F_published.setBounds(110,120, 200,30);

            JTextField F_abId =new JTextField();
            F_abId.setBounds(110, 155, 200, 30);

            JTextField F_pages =new JTextField();
            F_pages.setBounds(110, 190, 200, 30);

            JTextField F_language =new JTextField();
            F_language.setBounds(110, 225, 200, 30);

            JTextField F_price =new JTextField();
            F_price.setBounds(110, 260, 200, 30);

            JButton create_but=new JButton("Submit");
            create_but.setBounds(130,300,80,25);
            create_but.addActionListener(e12 -> {
                String acId = F_acId.getText();
                String bookName = F_book.getText();
                String author = F_author.getText();
                String published = F_published.getText();
                String abId = F_abId.getText();
                int pages = Integer.parseInt(F_pages.getText());
                String lang = F_language.getText();
                Double price = Double.parseDouble(F_price.getText());

                if(bookService.addBook(new Books(acId, bookName, author, published, abId,
                        pages, lang, price, false))) {
                    JOptionPane.showMessageDialog(null, "Book Added to Database",
                            "Success message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "Book Already Exists",
                            "Error Message", JOptionPane.ERROR_MESSAGE);
            });

            g.add(l1);
            g.add(l2);
            g.add(l3);
            g.add(l4);
            g.add(l5);
            g.add(l6);
            g.add(l7);
            g.add(l8);

            g.add(F_acId);
            g.add(F_book);
            g.add(F_author);
            g.add(F_price);
            g.add(F_language);
            g.add(F_pages);
            g.add(F_published);
            g.add(F_abId);

            g.add(create_but);
            g.setSize(600,600);
            g.setLayout(null);
            g.setVisible(true);
            g.setLocationRelativeTo(null);

        });


        JButton issue_book=new JButton("Issue Book");
        issue_book.setBounds(450,20,120,25);
        issue_book.addActionListener(e -> {
            JFrame g = new JFrame("Enter Details");

            JLabel l1;
            l1=new JLabel("Accession ID");
            l1.setBounds(30,15, 100,30);

            JTextField F_bid = new JTextField();
            F_bid.setBounds(110, 15, 200, 30);

            JButton create_but=new JButton("Submit");
            create_but.setBounds(130,170,80,25);
            create_but.addActionListener(e13 -> {

                String bid = F_bid.getText();

                if(issueService.issueBook(new Issue(bid))) {
                    JOptionPane.showMessageDialog(null, "Book Issued",
                            "Success Message", JOptionPane.INFORMATION_MESSAGE);
                    g.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "The book is taken or not present",
                            "Error Message", JOptionPane.ERROR_MESSAGE);

            });


            g.add(F_bid);
            g.add(create_but);
            g.add(l1);

            /*g.add(l3);
            g.add(l4);
            g.add(l2);
            g.add(F_uid);
            g.add(F_period);
            g.add(F_issue);*/

            g.setSize(350,250);//400 width and 500 height
            g.setLayout(null);//using no layout managers
            g.setVisible(true);//making the frame visible
            g.setLocationRelativeTo(null);


        });


        JButton return_book=new JButton("Return Book");
        return_book.setBounds(280,60,160,25);
        return_book.addActionListener(e -> {

            JFrame g = new JFrame("Enter Details");
            //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //set labels
            JLabel l1,l2,l3,l4;
            l1=new JLabel("Issue ID(IID)");  //Label 1 for Issue ID
            l1.setBounds(30,15, 100,30);


            l4=new JLabel("Return Date(DD-MM-YYYY)");
            l4.setBounds(30,50, 150,30);

            JTextField F_iid = new JTextField();
            F_iid.setBounds(110, 15, 200, 30);


            JTextField F_return=new JTextField();
            F_return.setBounds(180, 50, 130, 30);


            JButton create_but = new JButton("Return");//creating instance of JButton to mention return date and calculate fine
            create_but.setBounds(130,170,80,25);
            create_but.addActionListener(e14 -> {

                String iid = F_iid.getText();
                String return_date = F_return.getText();

                /*Connection connection = connect();

                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("USE LIBRARY");
                    //Intialize date1 with NULL value
                    String date1=null;
                    String date2=return_date; //Intialize date2 with return date

                    //select issue date
                    ResultSet rs = stmt.executeQuery("SELECT ISSUED_DATE FROM ISSUED WHERE IID="+iid);
                    while (rs.next()) {
                        date1 = rs.getString(1);

                    }

                    try {
                        Date date_1=new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                        Date date_2=new SimpleDateFormat("dd-MM-yyyy").parse(date2);
                        //subtract the dates and store in diff
                        long diff = date_2.getTime() - date_1.getTime();
                        //Convert diff from milliseconds to days
                        ex.days=(int)(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));


                    } catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }


                    //update return date
                    stmt.executeUpdate("UPDATE ISSUED SET RETURN_DATE='"+return_date+"' WHERE IID="+iid);
                    g.dispose();


                    Connection connection1 = connect();
                    Statement stmt1 = connection1.createStatement();
                    stmt1.executeUpdate("USE LIBRARY");
                    ResultSet rs1 = stmt1.executeQuery("SELECT PERIOD FROM ISSUED WHERE IID="+iid); //set period
                    String diff=null;
                    while (rs1.next()) {
                        diff = rs1.getString(1);

                    }
                    int diff_int = Integer.parseInt(diff);
                    if(ex.days&amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;gt;diff_int) { //If number of days are more than the period then calculcate fine

                        //System.out.println(ex.days);
                        int fine = (ex.days-diff_int)*10; //fine for every day after the period is Rs 10.
                        //update fine in the system
                        stmt1.executeUpdate("UPDATE ISSUED SET FINE="+fine+" WHERE IID="+iid);
                        String fine_str = ("Fine: Rs. "+fine);
                        JOptionPane.showMessageDialog(null,fine_str);

                    }

                    JOptionPane.showMessageDialog(null,"Book Returned!");

                }


                catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(null, e1);
                }*/

            });
            g.add(l4);
            g.add(create_but);
            g.add(l1);
            g.add(F_iid);
            g.add(F_return);
            g.setSize(350,250);//400 width and 500 height
            g.setLayout(null);//using no layout managers
            g.setVisible(true);//making the frame visible
            g.setLocationRelativeTo(null);
        });

        f.add(return_book);
        f.add(issue_book);
        f.add(add_book);
        f.add(issued_but);
        f.add(users_but);
        f.add(view_but);
        f.add(add_user);
        f.setSize(600,200);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setLocationRelativeTo(null);

    }

    /*
    public static void user_menu(String UID) {
        JFrame f=new JFrame("User Functions"); //Give dialog box name as User functions

        JButton view_but=new JButton("View Books");//creating instance of JButton
        view_but.setBounds(20,20,120,25);//x axis, y axis, width, height
        view_but.addActionListener(e -> {

            JFrame f12 = new JFrame("Books Available"); //View books stored in database
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            Connection connection = connect();
            String sql="select * from BOOKS"; //Retreive data from database
            try {
                Statement stmt = connection.createStatement(); //connect to database
                stmt.executeUpdate("USE LIBRARY"); // use librabry
                stmt=connection.createStatement();
                ResultSet rs=stmt.executeQuery(sql);
                JTable book_list= new JTable(); //show data in table format
                book_list.setModel(DbUtils.resultSetToTableModel(rs));

                JScrollPane scrollPane = new JScrollPane(book_list); //enable scroll bar

                f12.add(scrollPane); //add scroll bar
                f12.setSize(800, 400); //set dimensions of view books frame
                f12.setVisible(true);
                f12.setLocationRelativeTo(null);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, e1);
            }

        }
        );

        JButton my_book=new JButton("My Books");//creating instance of JButton
        my_book.setBounds(150,20,120,25);//x axis, y axis, width, height
        my_book.addActionListener(e -> {


            JFrame f1 = new JFrame("My Books"); //View books issued by user
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            int UID_int = Integer.parseInt(UID); //Pass user ID

            //.iid,issued.uid,issued.bid,issued.issued_date,issued.return_date,issued,
            Connection connection = connect(); //connect to database
            //retrieve data
            String sql="select distinct issued.*,books.bname,books.genre,books.price from issued,books " + "where ((issued.uid=" + UID_int + ") and (books.bid in (select bid from issued where issued.uid="+UID_int+"))) group by iid";
            String sql1 = "select bid from issued where uid="+UID_int;
            try {
                Statement stmt = connection.createStatement();
                //use database
                stmt.executeUpdate("USE LIBRARY");
                stmt=connection.createStatement();
                //store in array
                ArrayList books_list = new ArrayList();



                ResultSet rs=stmt.executeQuery(sql);
                JTable book_list= new JTable(); //store data in table format
                book_list.setModel(DbUtils.resultSetToTableModel(rs));
                //enable scroll bar
                JScrollPane scrollPane = new JScrollPane(book_list);

                f1.add(scrollPane); //add scroll bar
                f1.setSize(800, 400); //set dimensions of my books frame
                f1.setVisible(true);
                f1.setLocationRelativeTo(null);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, e1);
            }

        }
        );



        f.add(my_book); //add my books
        f.add(view_but); // add view books
        f.setSize(300,100);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setLocationRelativeTo(null);
    }

     */
}
