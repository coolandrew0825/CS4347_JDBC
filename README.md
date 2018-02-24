# CS4347_JDBC
class name: database system
programming languages: Java and SQL

This project is about creating a schema for address, crediservices and DAOs(Data Access Objects)

AddressDAO is an interface for AddressDaoImpl class.
CreditCardDAO is an interface for CreditCardDaoImpl class.
CustomerDAO is an interface for CustomerDaoImpl class.
ProductDAO is an interface for ProductDaoImpl class.
PurchaseDAO is an interface for PurchaseDaoImpl class.

AddressDaoImpl has create, delete, and retrieve functions.
CreditCardDaoImpl has create, delete, and retrieve functions.
CustomerDaoImpl has create, delete, and retrieve functions.
ProductDaoImpl has create, delete, and retrieve functions.
PurchaseDaoImpl has create, delete, and retrieve functions.

Address contains getter and setter functions for the private variables.
CreditCard contains getter and setter functions for the private variables.
Customer contains getter and setter functions for the private variables.
Product contains getter and setter functions for the private variables.
Purchase contains getter and setter functions for the private variables.

CustomerPersistenceService is an interface for CustomerPersistenceServiceImpl class.
ProductPersistenceService is an interface for ProductPersistenceServiceImpl class.
PurchasePersistenceService is an interface for PurchasePersistenceServiceImpl class.
PurchaseSummary returns a summary of purchase

CustomerPersistenceServiceImpl has create, retrieve, update, delete, retrieveByZipCode, and retrieveByDOB functions.
ProductPersistenceServiceImpl has create, retrieve, update, delete, retrieveByUPC, and retrieveByCategory functions.
PurchasePersistenceServiceImpl has create, retrieve, update, delete functions.

DAOException class handles exception.
