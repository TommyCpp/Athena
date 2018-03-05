import {Inject, Injectable} from '@angular/core';
import {BASE_URL, REST_URL} from '../../config';
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
  private endPointMap: { [key: string]: EndPoint } = {};

  constructor(@Inject(REST_URL) rawEndPointMap: { [key: string]: EndPoint }, @Inject(BASE_URL) baseUrl: string) {
    for (const key in rawEndPointMap) {
      let endPoint = rawEndPointMap[key];
      endPoint.url = baseUrl + endPoint.url;
      this.endPointMap[key] = endPoint
    }
    for (const key in this.endPointMap) {
      if (this.endPointMap.hasOwnProperty(key)) {
        if (this.endPointMap[key].needAuth) {
          this.endPointNeedAuth.push(this.endPointMap[key]);
        } else {
          this.endPointWithoutAuth.push(this.endPointMap[key]);
        }
      }
    }
    this.endPoints = Object.values(this.endPointMap);
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

  public getUrl(key: string, pathParams?: { [key: string]: string }): string {
    if (pathParams) {
      return this.setPathParams(this.endPointMap[key], pathParams).url;
    }
    else {
      return this.endPointMap[key].url;
    }
  }

  public setPathParams(endPoint: EndPoint, pathParams: { [key: string]: string | number }): EndPoint {
    endPoint.url = endPoint.url.replace(/{[a-zA-Z]*}/g, (match) => {
      return pathParams[match.substr(1, match.length - 2)] as string;
    });
    return endPoint;
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
