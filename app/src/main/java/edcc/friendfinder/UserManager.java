package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserManager {

    private static UserManager um;
    private ArrayList<User> users;
    private DataHandler dh;

    private UserManager() {
        dh = new DataHandler();
        users = dh.getUsers();
    }

    public static UserManager getUserManager() {
        if (um == null) {
            um = new UserManager();
        }
        return um;
    }

    public List<User> getUsers() {
        Collections.sort(users);
        return users;
    }

//    public String[] getNames() {
//
//    }

    public User getUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
        dh.addUsers(users);
    }

    public void deleteUser(User user) {
        users.remove(user);
        dh.addUsers(users);
    }

    public void updateUser(User user) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                index = i;
            }
        }
        users.get(index).setFriends(user.getFriends());
        users.get(index).setInterests(user.getInterests());
        users.get(index).setMajor(user.getMajor());
        users.get(index).setFirstName(user.getFirstName());
        users.get(index).setLastName(user.getLastName());
    }

    /* Replaces a pet object in the pet list when a user has updated details.
     *
     * @param pet the pet object with which to replace the existing one
     */
    void replaceUser(User pet) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == pet.getId()) {
                users.set(i, pet);
            }
        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("pets")
//                .document(String.valueOf(pet.getPetId())).set(pet);
    }
}
///**
// * Manages the list of Pet objects.
// *
// * @author Linda Zuvich
// * @version 12/21/2018
// */
//public class DataManager {
//
//    //fields
//    private static DataManager dm;
//    private List<Pet> petList;
//    private int nextPetId;
//    private List<Vet> vetList;
//    private int nextVetId;
//    private List<Client> clientList;
//    private int nextClientId;
//    private final PreferencesManager pm;
//    private static String userId;
//
//    /*
//     * Private constructor.
//     **/
//    private DataManager(Context ctx) {
//        pm = PreferencesManager.getInstance(ctx);
//        petList = new ArrayList<>();
//        vetList = new ArrayList<>();
//        clientList = new ArrayList<>();
//    }
//
//    /**
//     * Singleton implementation - returns the single instance of
//     * the DataManager class.
//     */
//    public static DataManager getDataManager(Context ctx, String userId) {
//        DataManager.userId = userId;
//        if (dm == null) {
//            dm = new DataManager(ctx);
//        }
//        return dm;
//    }
//
//    /**
//     * Provides access to a sorted list of all pets.
//     * Sorts in reverse if user preferences require it.
//     *
//     * @return List<Pet> - the list of pets
//     */
//    List<Pet> getPetList() {
//        Collections.sort(petList);
//        if (!pm.isSortAZ()) {
//            Collections.reverse(petList);
//        }
//        return petList;
//    }
//
//    /**
//     * Replaces the pet list when Firebase has pushed an update to
//     * an activity. Does not require updating the DB since it came from the DB.
//     *
//     * @param list the new list of Pet objects
//     */
//    void setPetList(List<Pet> list) {
//        Collections.sort(list);
//        petList = list;
//        if (petList.size() > 0) {
//            nextPetId = petList.get(0).getPetId();
//            for (Pet p : petList) {
//                if (p.getPetId() > nextPetId) {
//                    nextPetId = p.getPetId();
//                }
//            }
//            nextPetId++;
//        }
//    }
//}

//    /**
//     * Provides access to one pet.
//     *
//     * @param id the pet's id number
//     * @return the Pet object
//     */
//    Pet getPet(int id) {
//        //search for id
//        int index = -1;
//        for (int i = 0; i < petList.size(); i++) {
//            Pet p = petList.get(i);
//            if (p.getPetId() == id) {
//                index = i;
//                break;
//            }
//        }
//        //if found
//        if (index >= 0) {
//            return petList.get(index);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Replaces a pet object in the pet list when Firebase has pushed an update to
//     * an activity. Does not require updating the DB since it came from the DB.
//     *
//     * @param pet the pet object with which to replace the existing one
//     */
//    public void setPet(Pet pet) {
//        for (int i = 0; i < petList.size(); i++) {
//            if (petList.get(i).getPetId() == pet.getPetId()) {
//                petList.set(i, pet);
//            }
//        }
//    }
//
//    /**

//
//    /**
//     * Adds a Pet object to the list of pets, maintaining sorted order.
//     *
//     * @param newPet the new Pet object
//     */
//    void addPet(Pet newPet) {
//        if (newPet == null) {
//            return;
//        }
//        newPet.setPetId(nextPetId);
//        petList.add(newPet);
//        nextPetId++;
//        Collections.sort(petList);
//        if (!pm.isSortAZ()) {
//            Collections.reverse(petList);
//        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("pets")
//                .document(String.valueOf(newPet.getPetId())).set(newPet);
//    }
//
//    /**
//     * Deletes the given pet from the list.
//     *
//     * @param id the id of the pet to delete
//     */
//    void deletePet(int id) {
//        int index = -1;
//        //find pet in list
//        for (int i = 0; i < petList.size(); i++) {
//            Pet p = petList.get(i);
//            if (p.getPetId() == id) {
//                index = i;
//                break;
//            }
//        }
//        //if found
//        if (index >= 0) {
//            //delete
//            petList.remove(index);
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("users").document(userId).collection("pets")
//                    .document(String.valueOf(id)).delete();
//        }
//    }
//
//    /**
//     * Reports if the client with the given ID has any pets in the system.
//     *
//     * @param clientId the ID of the client
//     * @return true if they have at least one pet, false if not
//     */
//    boolean clientHasPets(int clientId) {
//        int index = -1;
//        for (int i = 0; i < petList.size() && index < 0; i++) {
//            if (petList.get(i).getClientId() == clientId) {
//                index = i;
//            }
//        }
//        return index > -1;
//    }
//
//    /**
//     * Reports if the vet with the given ID has any pets in the system.
//     *
//     * @param vetId the ID of the vet
//     * @return true if they have at least one pet, false if not
//     */
//    boolean vetHasPets(int vetId) {
//        int index = -1;
//        for (int i = 0; i < petList.size() && index < 0; i++) {
//            if (petList.get(i).getVetId() == vetId) {
//                index = i;
//            }
//        }
//        return index > -1;
//    }
//
//    /**
//     * Provides access to a sorted list of all vets.
//     * Sorts in reverse if user preferences require it.
//     *
//     * @return List<Vet> - the list of vets
//     */
//    List<Vet> getVetList() {
//        Collections.sort(vetList);
//        return vetList;
//    }
//
//    /**
//     * Replaces the vet list when Firebase has pushed an update to
//     * an activity. Does not require updating the DB since it came from the DB.
//     *
//     * @param list the new list of Vet objects
//     */
//    void setVetList(List<Vet> list) {
//        Collections.sort(list);
//        vetList = list;
//        if (vetList.size() > 0) {
//            nextVetId = vetList.get(0).getVetId();
//            for (Vet v : vetList) {
//                if (v.getVetId() > nextVetId) {
//                    nextVetId = v.getVetId();
//                }
//            }
//            nextVetId++;
//        }
//    }
//
//    /**
//     * Provides access to one vet.
//     *
//     * @param id the vet's id number
//     * @return the Vet object
//     */
//    Vet getVet(int id) {
//        //search for id
//        int index = -1;
//        for (int i = 0; i < vetList.size(); i++) {
//            Vet p = vetList.get(i);
//            if (p.getVetId() == id) {
//                index = i;
//                break;
//            }
//        }
//        //if found
//        if (index >= 0) {
//            return vetList.get(index);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Replaces a vet object in the vet list when Firebase has pushed an update to
//     * an activity. Does not require updating the DB since it came from the DB.
//     *
//     * @param vet the vet object with which to replace the existing one
//     */
//    public void setVet(Vet vet) {
//        for (int i = 0; i < vetList.size(); i++) {
//            if (vetList.get(i).getVetId() == vet.getVetId()) {
//                vetList.set(i, vet);
//            }
//        }
//    }
//
//    /**
//     * Replaces a vet object in the vet list when a user has updated details.
//     *
//     * @param vet the vet object with which to replace the existing one
//     */
//    void replaceVet(Vet vet) {
//        for (int i = 0; i < vetList.size(); i++) {
//            if (vetList.get(i).getVetId() == vet.getVetId()) {
//                vetList.set(i, vet);
//            }
//        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("vets")
//                .document(String.valueOf(vet.getVetId())).set(vet);
//
//    }
//
//
//    /**
//     * Adds a Vet object to the list of vets, maintaining sorted order.
//     *
//     * @param newVet the new Vet object
//     * @return the ID assigned to the new Vet object, or -1 if not added
//     */
//    int addVet(Vet newVet) {
//        if (newVet == null) {
//            return -1;
//        }
//        newVet.setVetId(nextVetId);
//        vetList.add(newVet);
//        nextVetId++;
//        Collections.sort(vetList);
//        if (!pm.isSortAZ()) {
//            Collections.reverse(vetList);
//        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("vets")
//                .document(String.valueOf(newVet.getVetId())).set(newVet);
//
//        return newVet.getVetId();
//    }
//
//    /**
//     * Deletes the given vet from the list.
//     *
//     * @param id the id of the vet to delete
//     */
//    void deleteVet(int id) {
//        int index = -1;
//        //find vet in list
//        for (int i = 0; i < vetList.size(); i++) {
//            Vet p = vetList.get(i);
//            if (p.getVetId() == id) {
//                index = i;
//                break;
//            }
//        }
//        //if found
//        if (index >= 0) {
//            //delete
//            vetList.remove(index);
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("users").document(userId).collection("vets")
//                    .document(String.valueOf(id)).delete();
//        }
//    }
//
//    /**
//     * Provides access to a sorted list of all clients.
//     * Sorts in reverse if user preferences require it.
//     *
//     * @return List<Client> - the list of clients
//     */
//    List<Client> getClientList() {
//        Collections.sort(clientList);
//        return clientList;
//    }
//
//    /**
//     * Replaces the client list when Firebase has pushed an update to
//     * an activity. Does not require updating the DB since it came from the DB.
//     *
//     * @param list the new list of Client objects
//     */
//    void setClientList(List<Client> list) {
//        Collections.sort(list);
//        clientList = list;
//        if (clientList.size() > 0) {
//            nextClientId = clientList.get(0).getClientId();
//            for (Client c : clientList) {
//                if (c.getClientId() > nextClientId) {
//                    nextClientId = c.getClientId();
//                }
//            }
//            nextClientId++;
//        }
//    }
//
//    /**
//     * Provides access to one client.
//     *
//     * @param id the client's id number
//     * @return the Client object
//     */
//    Client getClient(int id) {
//        //search for id
//        int index = -1;
//        for (int i = 0; i < clientList.size(); i++) {
//            Client p = clientList.get(i);
//            if (p.getClientId() == id) {
//                index = i;
//                break;
//            }
//        }
//        //if found
//        if (index >= 0) {
//            return clientList.get(index);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Replaces a client object in the client list when Firebase has pushed an update to
//     * an activity. Does not require updating the DB since it came from the DB.
//     *
//     * @param client the client object with which to replace the existing one
//     */
//    public void setClient(Client client) {
//        for (int i = 0; i < clientList.size(); i++) {
//            if (clientList.get(i).getClientId() == client.getClientId()) {
//                clientList.set(i, client);
//            }
//        }
//    }
//
//    /**
//     * Replaces a client object in the client list when a user has edited details.
//     *
//     * @param client the client object with which to replace the existing one
//     */
//    void replaceClient(Client client) {
//        for (int i = 0; i < clientList.size(); i++) {
//            if (clientList.get(i).getClientId() == client.getClientId()) {
//                clientList.set(i, client);
//            }
//        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("clients")
//                .document(String.valueOf(client.getClientId())).set(client);
//    }
//
//    /**
//     * Adds a Client object to the list of clients, maintaining sorted order.
//     *
//     * @param newClient the new Client object
//     * @return the ID assigned to the new Client object, or -1 if not added
//     */
//    int addClient(Client newClient) {
//        if (newClient == null) {
//            return -1;
//        }
//        newClient.setClientId(nextClientId);
//        clientList.add(newClient);
//        nextClientId++;
//        Collections.sort(clientList);
//        if (!pm.isSortAZ()) {
//            Collections.reverse(clientList);
//        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("clients")
//                .document(String.valueOf(newClient.getClientId())).set(newClient);
//        return newClient.getClientId();
//    }
//
//    /**
//     * Deletes the given client from the list.
//     *
//     * @param id the id of the client to delete
//     */
//    void deleteClient(int id) {
//        int index = -1;
//        //find client in list
//        for (int i = 0; i < clientList.size(); i++) {
//            Client c = clientList.get(i);
//            if (c.getClientId() == id) {
//                index = i;
//                break;
//            }
//        }
//        //if found
//        if (index >= 0) {
//            //delete
//            clientList.remove(index);
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("users").document(userId).collection("clients")
//                    .document(String.valueOf(id)).delete();
//        }
//    }

