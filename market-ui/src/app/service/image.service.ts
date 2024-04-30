import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor() { }

  imageUrl(imageId?: number | null): string {
    return imageId ? environment.imgDownloadUrl + imageId : '/assets/no-image.png'
  }
}
