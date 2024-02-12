import { Method } from "axios";

export type AxiosParams = {
    method?: Method;
    url: string;
    data?: object;
    /* abaixo está os tipos dos query params */
    params?: object;
  };
  