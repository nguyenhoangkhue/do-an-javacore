public class User {
    private String username;
    private String password;
    private String[] booksHaveBeenRedId;
    private String bookAreBorrowingId;
    public User(){
    }
    public User(String userName, String password,String[] booksHaveBeenRedId,String bookAreBorrowingId){
        this.username=userName;
        this.password=password;
        this.booksHaveBeenRedId=booksHaveBeenRedId;
        this.bookAreBorrowingId=bookAreBorrowingId;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getBooksHaveBeenRedId() {
        return booksHaveBeenRedId;
    }

    public void setBooksHaveBeenRedId(String[] booksHaveBeenRedId) {
        this.booksHaveBeenRedId = booksHaveBeenRedId;
    }

    public String getBookAreBorrowingId() {
        return bookAreBorrowingId;
    }

    public void setBookAreBorrowingId(String bookAreBorrowingId) {
        this.bookAreBorrowingId = bookAreBorrowingId;
    }
    User(String username,String password){}
}
