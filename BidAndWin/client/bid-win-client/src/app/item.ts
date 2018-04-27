export interface Item {
    id?: number;
    name?: String;
    picture? : String;
    currentPrice? : number;
    bidIncrement? : number;
    buyItPrice? : number;
    description? : String;
    endTime? : Date;
    categoryId? : number;
    userId? : number;
    startPrice : number;
  }