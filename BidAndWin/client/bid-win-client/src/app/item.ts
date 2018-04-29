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
    user? : User;
    startPrice? : number;
    file? : File;
    bestBidderId?: number;
  }