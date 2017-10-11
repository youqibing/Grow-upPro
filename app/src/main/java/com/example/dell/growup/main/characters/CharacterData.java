package com.example.dell.growup.main.characters;

import com.example.dell.growup.network.result.CharactersResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/10.
 */

public class CharacterData {

    private List<CharactersResult.CharacterList> characterLists;
    private List<CharactersResult.HeaderList> headerLists;
    private List<CharactersResult.BottomList> bottomLists;

    public CharacterData(){
        characterLists = new ArrayList<>();
        headerLists = new ArrayList<>();
        bottomLists = new ArrayList<>();
    }

    public List<CharactersResult.CharacterList> getCharacter(){
        CharactersResult.CharacterList wumingLists = new CharactersResult.CharacterList();
        CharactersResult.CharacterList AlixiyaLists = new CharactersResult.CharacterList();

        wumingLists.setImage("wuming");
        wumingLists.setName("初级小人");
        wumingLists.setDescribe("初级小人，最初始陪伴玩家的人物,是个离不开围巾的人");

        AlixiyaLists.setImage("alixiya");
        AlixiyaLists.setName("艾丽西亚");
        AlixiyaLists.setDescribe("没人知道她来自哪里,她是森林中美丽的女性鬼魂,喜欢调戏路人");

        characterLists.add(wumingLists);
        characterLists.add(AlixiyaLists);

        return characterLists;
    }

    public List<CharactersResult.HeaderList> getHeaderList(){
        CharactersResult.HeaderList headerList = new CharactersResult.HeaderList();

        headerList.setImg("froest");
        headerList.setText("大吉大利，晚上吃鸡!");

        headerLists.add(headerList);

        return headerLists;
    }

    public List<CharactersResult.BottomList> getBottomList(){
        CharactersResult.BottomList bottomList = new CharactersResult.BottomList();

        bottomList.setImg("");
        bottomList.setText("我是有底线的额！");

        bottomLists.add(bottomList);

        return bottomLists;
    }
}
