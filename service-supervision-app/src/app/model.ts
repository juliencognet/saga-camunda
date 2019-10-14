export interface StockLevel{
    maxStockLevel: number;
    currentStockLevel: number;
}
export interface BookingTransaction{
    guid: string;
    transactionStatus: string;
    transactionAmount: string;
    transactionDate: Date;
}
export interface StateAndTransactions{
    stockLevel: StockLevel;
    bookingTransactions: BookingTransaction[];
}