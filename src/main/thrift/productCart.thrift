namespace java com.example

struct Product {
  1:i32 id,
  2:string name,
  3:double price,
  4:i32 amountInStoreage
}
struct OrderItem {
  1:i32 id,
  2:i32 amount
}
exception NotEnoughInStoreageException {
}
service ProductCart {
  void addItem(1:OrderItem item)
  void removeItem(1:i32 id)
  void updateItem(1:OrderItem item)
  void placeOrder() throws (1:NotEnoughInStoreageException notEnoughInStoreage)

}

service ProductManager {
   list<Product> getAllProducts()
}
