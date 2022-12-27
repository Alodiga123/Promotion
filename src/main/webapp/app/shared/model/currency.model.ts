export interface ICurrency {
  id?: number;
  name?: string;
  description?: string;
  simbol?: string;
}

export class Currency implements ICurrency {
  constructor(public id?: number, public name?: string, public description?: string, public simbol?: string) {}
}
