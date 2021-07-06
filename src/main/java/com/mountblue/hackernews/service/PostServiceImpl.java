package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.Post;
import com.mountblue.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllByAskHN() {
        return postRepository.findAllByAskHN();

    }

    @Override
    public void savePost(Post post) {
//        post.setHide(false);
        postRepository.save(post);
    }

    @Override
    public Post getPostById(Integer id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAllByShowHN() {
        return postRepository.findAllByShowHN();
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findPaginated(Integer pageNo, Integer pageSize, String sortingField, String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postRepository.findAll(pageable);
    }

    @Override
    public List<Post> getPostByKeyWord(String keyWord, String startDate, String endDate) {
        if(!startDate.isEmpty() && !endDate.isEmpty()) {
            Timestamp dateFrom = Timestamp.from(Instant.parse(startDate + ":00.000Z"));
            Timestamp dateTo = Timestamp.from(Instant.parse(endDate + ":00.000Z"));

            Calendar calDateFrom = Calendar.getInstance();
            calDateFrom.setTimeInMillis(dateFrom.getTime());

            // subtract 30 minutes
            calDateFrom.add(Calendar.MINUTE, -30);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            // subtract 5 hours
            calDateFrom.setTimeInMillis(dateFrom.getTime());
            calDateFrom.add(Calendar.HOUR, -5);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            Calendar calDateTo = Calendar.getInstance();
            calDateTo.setTimeInMillis(dateTo.getTime());

            // subtract 30 minutes
            calDateTo.add(Calendar.MINUTE, -30);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            // subtract 5 hours
            calDateTo.setTimeInMillis(dateTo.getTime());
            calDateTo.add(Calendar.HOUR, -5);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            return this.postRepository.findAllByKeyWordWithTimeDate(keyWord, dateFrom, dateTo);
        }
        return postRepository.findAllByKeyWordDescOrder(keyWord);
    }

    @Override
    public void deletePostById(int postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getPostByKeyWordWithPoints(String keyWord, String startDate, String endDate) {
        if(!startDate.isEmpty() && !endDate.isEmpty()) {
            Timestamp dateFrom = Timestamp.from(Instant.parse(startDate + ":00.000Z"));
            Timestamp dateTo = Timestamp.from(Instant.parse(endDate + ":00.000Z"));

            Calendar calDateFrom = Calendar.getInstance();
            calDateFrom.setTimeInMillis(dateFrom.getTime());

            // subtract 30 minutes
            calDateFrom.add(Calendar.MINUTE, -30);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            // subtract 5 hours
            calDateFrom.setTimeInMillis(dateFrom.getTime());
            calDateFrom.add(Calendar.HOUR, -5);
            dateFrom = new Timestamp(calDateFrom.getTime().getTime());

            Calendar calDateTo = Calendar.getInstance();
            calDateTo.setTimeInMillis(dateTo.getTime());

            // subtract 30 minutes
            calDateTo.add(Calendar.MINUTE, -30);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            // subtract 5 hours
            calDateTo.setTimeInMillis(dateTo.getTime());
            calDateTo.add(Calendar.HOUR, -5);
            dateTo = new Timestamp(calDateTo.getTime().getTime());

            System.out.println(dateFrom+"    "+dateTo);
            return postRepository.findAllByKeyWordWithPoints(keyWord, dateFrom, dateTo);
        }
        return postRepository.findAllByKeyWordPopularDescOrder(keyWord);
    }

    /*@Override
    public List<Post> getSortedList(List<Post> postsList) {
        return postRepository.
    }*/
}
