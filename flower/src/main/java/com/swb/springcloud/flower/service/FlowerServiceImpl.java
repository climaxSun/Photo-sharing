package com.swb.springcloud.flower.service;

import com.swb.springcloud.flower.common.RestResponse;
import com.swb.springcloud.flower.exception.IllegalParamsException;
import com.swb.springcloud.flower.feign.Client.ESFeignClient;
import com.swb.springcloud.flower.pojo.Flower;
import com.swb.springcloud.flower.pojo.User;
import com.swb.springcloud.flower.pojo.Vote;
import com.swb.springcloud.flower.repository.FlowerRepository;
import com.swb.springcloud.flower.repository.UserRepository;
import com.swb.springcloud.flower.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlowerServiceImpl implements FlowerService{

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ESFeignClient esFeignClient;

    @Transactional
    @Override
    public Flower saveFlower(Flower flower) {
        Flower returnFlower = flowerRepository.save(flower);
        esFeignClient.saveEsFlower(returnFlower);
        userRepository.updateSharesNumber(flower.getUserId());
        return returnFlower;
    }

    @Transactional
    @Override
    public void removeFlower(Long id) {
        flowerRepository.deleteById(id);
    }

    @Override
    public Flower getFlowerById(Long id) {
        Flower flower=flowerRepository.findById(id).orElse(null);
        if(flower==null){
            throw new IllegalParamsException(IllegalParamsException.Type.FLOWER_NOT_FIND,"id为"+id+"的花图没找到");
        }
        return flower;
    }

    @Override
    public List<Flower> getFlowerByAllId(List<Long> id) {
        return flowerRepository.findAllByIdInOrderByCreateTimeDesc(id, PageRequest.of(0,id.size())).getContent();
    }

    @Override
    public Page<Flower> getFlowersByUser_id(Long id, Long userId, Pageable pageable, String keyword) {
        Page<Flower> flowerPage=flowerRepository.findFlowersByUserIdAndTagsLikeOrderByCreateTimeDesc(id,pageable,"%"+keyword+"%");
        List<Flower> flowers=flowerPage.getContent();
        Optional<User> OptionalUser = userRepository.findById(id);
        if(!OptionalUser.isPresent()) {
            return flowerPage;
        }
        User User=OptionalUser.get();
        for(Flower flower:flowers){
            if(User.getAvatar()!=null)
                flower.setUserAvatar(User.getAvatar());
            flower.setUserName(User.getName());
        }
        if(userId.equals(0))
            return flowerPage;
        List<Long> flowers_id=new ArrayList<>();
        for(Flower flower :flowers)
            flowers_id.add(flower.getId());
        List<Vote> votes=voteRepository.findVotesByUserIdAndFlowerIdIn(userId,flowers_id);
        for(Vote vote:votes){
            for(int i=0;i<flowers.size();i++){
                Flower flower=flowers.get(i);
                if(vote.getFlowerId().equals(flower.getId())){
                    flower.setIsVote(true);
                    break;
                }
            }
        }
        return flowerPage;
    }


    @Override
    public boolean saveEsFlower(){
        List<Flower> flowers=flowerRepository.findAll();
        List<Flower> flowerList=new ArrayList<>();
        System.out.println(flowers);
        for(int i=0;i<flowers.size();i++){
            Flower flower=flowers.get(i);
            User u=userRepository.findById(flower.getUserId()).get();
            flower.setUserAvatar(u.getAvatar());
            flower.setUserName(u.getName());
            flowerList.add(flower);
        }
        RestResponse<Object> restResponse= esFeignClient.saveEsFlowers(flowerList);
        System.out.println(restResponse.toString());
        return true;
    }
}
