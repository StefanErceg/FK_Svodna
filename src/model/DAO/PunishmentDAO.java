package model.DAO;

import model.DTO.Punishment;

import java.util.List;

public interface PunishmentDAO {

    List<Punishment> panishments();
    List<Punishment> getPanishmentForPlayersId(int id);
    boolean insert(Punishment panishment);
    boolean update(Punishment panishment);
}
