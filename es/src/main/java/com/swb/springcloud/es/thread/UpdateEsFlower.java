package com.swb.springcloud.es.thread;

import com.swb.springcloud.es.pojo.EsFlower;
import com.swb.springcloud.es.pojo.Flower;
import com.swb.springcloud.es.service.EsFlowerService;

import java.util.List;

public class UpdateEsFlower implements Runnable {

    private EsFlowerService esFlowerService;

    private List<Flower> flowers;

    public UpdateEsFlower(EsFlowerService esFlowerService, List<Flower> flowers) {
        this.esFlowerService = esFlowerService;
        this.flowers = flowers;
    }

    @Override
    public void run() {
        for(Flower flower:flowers){
            System.out.println("flower="+flower);
            EsFlower esFlower= esFlowerService.updateEsFlower(flower);
            System.out.println(esFlower.toString());
        }
    }
}
