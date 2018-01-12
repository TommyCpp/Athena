import {Inject, Injectable} from '@angular/core';
import {REST_URL} from '../../config';
import {HttpRequest} from '@angular/common/http';

export type HttpMethod = 'POST' | 'GET' | 'OPTION' | 'PUT' | 'PATCH'

export type EndPoint = {
  url: string;
  needAuth: boolean;
  type: HttpMethod[];
}

@Injectable()
export class EndPointService {
  private endPoints: EndPoint[];
  private endPointNeedAuth: EndPoint[] = [];
  private endPointWithoutAuth: EndPoint[] = [];

  constructor(@Inject(REST_URL) private endPointMap: { [key: string]: EndPoint }) {
    for (const key in endPointMap) {
      if (endPointMap.hasOwnProperty(key)) {
        if (endPointMap[key].needAuth) {
          this.endPointNeedAuth.push(endPointMap[key]);
        } else {
          this.endPointWithoutAuth.push(endPointMap[key]);
        }
      }
    }
    this.endPoints = Object.values(endPointMap);
  }

  public isRequestIsLogin(req: HttpRequest<any>): boolean {
    return this.endPointMap.hasOwnProperty('Login') && this.endPointMap['Login'].url == req.url && this.endPointMap['Login'].type.indexOf(<HttpMethod>req.method) !== -1;
  }

  public isRequestNeedAuth(req: HttpRequest<any>): boolean {
    return EndPointService.hasEndPoint(this.endPointNeedAuth, req);
  }

  public getEndPoint(key: string): EndPoint {
    return this.endPointMap[key];
  }

  private static hasEndPoint(endPoints: EndPoint[], req: HttpRequest<any>): boolean {
    for (let endPoint of endPoints) {
      if (EndPointService.matchEndPoint(endPoint, req)) {
        return true;
      }
    }
    return false;
  }

  private static matchEndPoint(endPoint: EndPoint, req: HttpRequest<any>): boolean {
    let url = req.url;
    let method = <HttpMethod>req.method;
    return endPoint.url === url && endPoint.type.indexOf(method) !== -1;
  }


}
