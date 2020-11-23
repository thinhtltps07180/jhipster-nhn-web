import { Moment } from 'moment';

export interface IPokerProfile {
  id?: number;
  ongameId?: string;
  nickName?: string;
  regDate?: Moment;
  lastDate?: Moment;
  photoPathContentType?: string;
  photoPath?: any;
  ip?: string;
}

export class PokerProfile implements IPokerProfile {
  constructor(
    public id?: number,
    public ongameId?: string,
    public nickName?: string,
    public regDate?: Moment,
    public lastDate?: Moment,
    public photoPathContentType?: string,
    public photoPath?: any,
    public ip?: string
  ) {}
}
