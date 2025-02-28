package com.truemen.api.post.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.truemen.api.common.exception.ServerException;
import com.truemen.api.post.dao.PostDao;
import com.truemen.api.post.dao.PostLikeDao;
import com.truemen.api.post.mapper.PostMapper;
import com.truemen.api.post.model.BulletScreenLike;
import com.truemen.api.post.model.Post;
import com.truemen.api.post.model.PostLike;
import com.truemen.api.post.query.PostUpdateQuery;
import com.truemen.api.post.query.PostUploadQuery;
import com.truemen.api.post.vo.PostDetailVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.truemen.api.post.dao.PostCollectionPostDao;

@Slf4j
@Service
public class PostService extends ServiceImpl<PostDao, Post> {

    @Autowired
    private PostCollectionPostDao postCollectionPostDao;
    @Autowired
    private PostLikeDao postLikeMapper;

    public PostDetailVo getPostDetail(Long postId) {

        Post post = baseMapper.selectById(postId);
        if (post == null)
            throw new ServerException("不存在postId=%d".formatted(postId));

        PostDetailVo postDetailVo = PostMapper.INSTANCE.postToPostDetailVo(post);

        return postDetailVo;
    }

    public Long upLoadPost(PostUploadQuery postUploadQuery) {
        Post post = PostMapper.INSTANCE.postUploadQueryToPost(postUploadQuery);
//        Debug.printFields(post);

        int affectLine = baseMapper.insert(post);
        if (affectLine!=1){
            return null;
        }else {
            return post.getPostId();
        }

        //todo
        //这里添加合集考虑前端调用添加合集api
        // 添加帖子到合集
//        if (postVo.getCollectionId() != null) {
//            PostCollectionPost collectionPost = PostCollectionPost.builder()
//                    .collectionId(postVo.getCollectionId())
//                    .postId(post.getPostId())
//                    .build();
//            postCollectionPostDao.insert(collectionPost);
//        }
//        return (long) baseMapper.insert(post);
    }

    public boolean deletePost(Integer pid) {
        int rows = baseMapper.deleteById(pid);
        if (rows == 0) {
            return false;
        }
        return true;
    }

    public boolean updatePost(Long pid, PostUpdateQuery query) {
        LambdaQueryWrapper<Post> qw = new LambdaQueryWrapper();
        qw.eq(Post::getPostId, pid);
        Post post = PostMapper.INSTANCE.postUpdateQueryToPost(query);
        int rows = baseMapper.update(post, qw);
        if (rows == 0)
            return false;
        else
            return true;
    }

    public Map<String, Long> getPostCountByLocation() {
        List<Post> posts = list();
        // return posts.stream()
        // .filter(post -> post.getLocation() != null)
        // .collect(Collectors.groupingBy(Post::getLocation, Collectors.counting()));
        return null;
    }

    public Boolean likePost(Long uid, Long postId, Boolean yes) {
        LambdaQueryWrapper<PostLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PostLike::getUserId,uid);
        queryWrapper.eq(PostLike::getPostId,postId);
        Long count = postLikeMapper.selectCount(queryWrapper);
//        log.info("count {}",count);
        if (count!=0 && !yes){
            return postLikeMapper.delete(queryWrapper) == 1;
        }
        if(count==0 && yes){
            PostLike like= new PostLike(null,postId,uid);
            return postLikeMapper.insert(like) == 1;
        }
        return true;
    }
}