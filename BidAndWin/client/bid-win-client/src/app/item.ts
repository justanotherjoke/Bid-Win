import {User} from './user';
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
    user? : User;
    startPrice : number;
  }