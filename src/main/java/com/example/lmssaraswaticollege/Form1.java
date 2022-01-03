package com.example.lmssaraswaticollege;

import com.example.lmssaraswaticollege.books.BookService;
import com.example.lmssaraswaticollege.books.Books;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Form1 extends JFrame{

    private final BookService bookService;

    private final String[] columnNames = {"Accession No", "Name", "Author", "Published In", "Abscission No", "No Of Pages",
            "Language", "Price"};
    private final String[] comboArr = {"All Books", "Accession No", "Name", "Author", "Published In", "Abscission No", "No Of Pages",
            "Language", "Price", "Issued"};
    private final Map<String, String> column = Stream.of(new String[][] {
            {"All Books", "All Books"},
            {"Accession No", "accNo"},
            {"Name", "bookName"},
            {"Author", "authorName"},
            {"Published In", "yearOfPub"},
            {"Abscission No", "abscissionNo"},
            {"No. OF Pages", "noOfPages"},
            {"Language", "language"},
            {"Price", "price"},
            {"Issued", "issued"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    /*{"Accession No": "accNo", "Name": "bookName", "Author": "authorName", "Published In": "yearOfPub",
            "Abscission No": "abscissionNo", "No. OF Pages": "noOfPages", "Language": "language", "Price": "price"}*/

    private JComboBox<String> field;
    private JTextField textField1;
    private JButton search;
    private JTable table1;
    private JScrollPane pane;
    private JPanel panelMain;

    public Form1(BookService bookService){
        this.bookService = bookService;
        setContentPane(panelMain);
        setSize(new Dimension(800, 600));
        setVisible(true);

        for (String columnName : comboArr) field.addItem(columnName);

        createTable(bookService.getAllBooks());
        searchQuery();
    }

    public void searchQuery(){
        search.addActionListener(e ->{
            String queryField = comboArr[field.getSelectedIndex()];
            String query = textField1.getText();

            queryField = column.get(queryField);

            createTable(bookService.getBooksByQuery(queryField, query));
        });
    }

    public void createTable(List<Books> bookList){
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Books books : bookList)
            modelAddRow(model, books);
        table1.setModel(model);

    }

    private void modelAddRow(DefaultTableModel model, Books books) {
        String AccNo = books.getAccNo();
        String name = books.getBookName();
        String author = books.getAuthorName();
        String publishedIn = books.getYearOfPub();
        String abNo = books.getAbscissionNo();
        int noOfPages = books.getNoOfPages();
        String language = books.getLanguage();
        Double price = books.getPrice();

        model.addRow(new Object[]{AccNo, name, author, publishedIn, abNo, noOfPages, language, price});
    }


}
