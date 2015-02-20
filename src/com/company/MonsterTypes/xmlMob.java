package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class xmlMob extends Monster{
    private List<String> meleeAttacks = new ArrayList<String>();
    private List<String> magicAttacks = new ArrayList<String>();
    private List<String> rangedAttacks = new ArrayList<String>();
    private List<String> failures = new ArrayList<String>();
    private int failChance = 0;
    private String name;

    public xmlMob(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("src/com/company/MonsterTypes/mobs.xml");
            document.normalizeDocument();
            Element mobs = document.getDocumentElement();

            if(mobs.getNodeName().equals("mobs")){
                NodeList mobList = mobs.getElementsByTagName("mob");
                Node mobToSpawn = null;

                if (mobList.getLength() > 1) {
                    int mobToGet = Console.RandomInt(0, mobList.getLength()-1);
                    mobToSpawn = mobList.item(mobToGet);
                }else if (mobList.getLength() == 1){
                    mobToSpawn = mobList.item(0);
                }else {
                    name = "stick";
                    meleeAttacks.add("hits you");
                }

                if (mobList.getLength() > 0){
                    if (mobToSpawn.hasAttributes()) {
                        name = ((Element) mobToSpawn).getAttribute("Name");
                    }else {
                        name = "xmlMob";
                    }

                    NodeList Attacks = mobToSpawn.getChildNodes();
                    if(Attacks.getLength() > 1){
                        if (Attacks.item(0).getNodeName().equals("allowedAttacks")) {
                            NodeList AllowedAttacks = Attacks.item(0).getChildNodes();
                            for (int i = 0; i < AllowedAttacks.getLength(); i++) {
                                attackTypes attackToAdd = attackTypes.valueOf(AllowedAttacks.item(i).getNodeName());
                                if (attackToAdd != null){
                                    Node Melee = ((Element)AllowedAttacks).getChildNodes().item(0);
                                    if(Melee.getNodeName().equals("messages")){
                                        NodeList messages = Melee.getChildNodes();
                                        for (int x = 0; x < messages.getLength(); x++) {
                                            if (messages.item(x).getNodeName().equals("msg")) {
                                                switch (attackToAdd){
                                                    case melee:
                                                        meleeAttacks.add(messages.item(x).getTextContent());
                                                        break;
                                                    case magic:
                                                        magicAttacks.add(messages.item(x).getTextContent());
                                                        break;
                                                    case ranged:
                                                        rangedAttacks.add(messages.item(x).getTextContent());
                                                        break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (Attacks.item(1).getNodeName().equals("failAttack")){
                            Node Failed = Attacks.item(1);
                            if(Failed.hasAttributes()){
                                try{
                                    failChance = Integer.parseInt( ((Element) Failed).getAttribute("failChance") );
                                }catch (Exception e){
                                    failChance = 50;
                                }
                            }

                            Node FailureMessages = Failed.getChildNodes().item(0);
                            if(FailureMessages.getNodeName().equals("messages")){
                                NodeList messages = FailureMessages.getChildNodes();
                                for (int i = 0; i < messages.getLength(); i++) {
                                    if (messages.item(i).getNodeName().equals("msg")) {
                                        failures.add(messages.item(i).getTextContent());
                                    }
                                }
                            }
                        }
                    }else {
                        meleeAttacks.add("hits you");
                    }
                }
                if (failures.size() == 0){
                    failChance = 0;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (meleeAttacks.size()+magicAttacks.size()+rangedAttacks.size() == 0){
            meleeAttacks.add("hits you");
            name = "stevie";
        }
    }

    /**
     * XMLMOB ATTACK!
     * @return amount of damage
     */
    @Override
    public int Attack() {
        int Damage = 0;
        if (Console.RandomInt(0,100) < failChance){
            failAttack();
        }else {
            int attackNr;
            if(meleeAttacks.size() + magicAttacks.size() + rangedAttacks.size() > 1) {
                attackNr = Console.RandomInt(0, (meleeAttacks.size() + magicAttacks.size() + rangedAttacks.size()));
            }else{
                attackNr = 1;
            }
            String attackString;
            if (attackNr <= meleeAttacks.size()){//melee
                Damage = MeleeAtt();
                attackString = meleeAttacks.get(attackNr-1);
            }else if ((attackNr - meleeAttacks.size()) <= magicAttacks.size()){ //magic
                Damage = MagicAtt();
                attackString = magicAttacks.get((attackNr-meleeAttacks.size()-1));
            }else{//ranged
                Damage = RangedAtt();
                attackString = magicAttacks.get((attackNr - meleeAttacks.size() - magicAttacks.size() - 1));
            }
            Console.Msg(name+" "+attackString+" and deals "+Damage + " Damage", false, true);
        }
        return Damage;
    }

    /**
     * XMLMOB failAttack, display message.
     */
    @Override
    public void failAttack(){
        int failureOption = Console.RandomInt(0, failures.size()-1);
        Console.Msg(name + " " + failures.get(failureOption), false, true);
    }
}
enum attackTypes{
        melee, magic, ranged;
}
