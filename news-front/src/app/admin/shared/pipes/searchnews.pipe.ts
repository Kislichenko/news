import {Pipe, PipeTransform} from '@angular/core';
import {News} from '../../../shared/interfaces';

@Pipe({
  name: 'searchNews'
})
export class SearchNewsPipe implements PipeTransform {
  transform(news: News[], search = ''): News[] {
    if (!search.trim()) {
      return news;
    } else {
      return news.filter(tmpNews => {
        return tmpNews.article.toLowerCase().includes(search.toLowerCase());
      });
    }
  }

}
