package core;

import static core.Aircraft.Airbus.*;
import static core.Aircraft.Boeing.*;
import static core.Aircraft.Bombardier.*;
import static core.Aircraft.Embraer.*;

/**
 * This code is kindly provided to Alexandr Dolgyi upon his request.
 *
 * Task Description:
 * Create a hierarchical enum structure in Java to represent different aircraft manufacturers and their specific models.
 * This structure should allow the creation of an aircraft instance by first selecting the manufacturer and then specifying the exact model.
 * Each manufacturer (e.g., Airbus, Boeing, Embraer, Bombardier) should have its own nested enum of models.
 */

public class EnumAircraftPractice {
  public static void main(String[] args) {

    Model modelAirbusA320 = Aircraft.Airbus.A320;
    Model modelBoeingB747 = Aircraft.Boeing.B747;
    Model modelEmbraerE190 = Aircraft.Embraer.E190;
    Model modelBombardierCL600 = Aircraft.Bombardier.CL600;

    System.out.println("Model: " + modelAirbusA320.getName());
    System.out.println("Model: " + modelBoeingB747.getName());
    System.out.println("Model: " + modelEmbraerE190.getName());
    System.out.println("Model: " + modelBombardierCL600.getName());
  }
}

interface Model {
  String getName();
}

enum Aircraft {
  AirbusProducer(new Airbus[]{A310, A320, A330}),
  BoeingProducer(new Boeing[]{B737, B747}),
  EmbraerProducer(new Embraer[]{E170, E190}),
  BombardierProducer(new Bombardier[]{CL300, CL600});

  private final Model[] models;

  Aircraft(Model[] models) {
    this.models = models;
  }

  public Model[] getModels() {
    return models;
  }


  enum Airbus implements Model {
    A310("A310"), A320("A320"), A330("A330");

    private final String name;

    Airbus(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  enum Boeing implements Model {
    B737("737"), B747("747");

    private final String name;

    Boeing(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  enum Embraer implements Model {
    E170("E170"), E190("E190");

    private final String name;

    Embraer(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  enum Bombardier implements Model {
    CL300("Challenger 300"), CL600("Challenger 600");

    private final String name;

    Bombardier(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}


