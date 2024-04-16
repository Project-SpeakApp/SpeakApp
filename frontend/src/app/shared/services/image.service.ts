import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import TypeMedia from "../types/media/type-media";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) { }

  public uploadImage(image: File, type: TypeMedia) {
    const formData = new FormData();
    formData.append('file', image);

    const params = new HttpParams().set('type', type);

    return this.http.post('http://localhost:8080/api/media', formData, { params });
  }
}
