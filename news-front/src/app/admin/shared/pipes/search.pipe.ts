import {Pipe, PipeTransform} from '@angular/core';
import {ReqData} from '../../../shared/interfaces';

@Pipe({
  name: 'searchReqDatas'
})
export class SearchPipe implements PipeTransform {
  transform(reqDatas: ReqData[], search = ''): ReqData[] {
    if (!search.trim()) {
      return reqDatas;
    } else {
      return reqDatas.filter(reqData => {
        return reqData.subject.toLowerCase().includes(search.toLowerCase());
      });
    }
  }

}
