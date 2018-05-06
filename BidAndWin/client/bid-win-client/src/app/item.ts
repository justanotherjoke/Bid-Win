import {User} from './user';
import { Category } from './category';
export interface Item {
    id?: number;
    name?: String;
    picture? : String;
    currentPrice? : number;
    bidIncrement? : number;
    buyItPrice? : number;
    description? : String;
    endTime? : Date;
    category? : Category;
    categoryId?: number;
    user? : User;
    startPrice? : number;
    file? : File;
    bestBidId?: number;
  }