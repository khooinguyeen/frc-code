/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import static frc.robot.Constants.CONTROLLER_CONST.*;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 * CÁI CLASS NÀY LÀ NƠI ĐỂ KHAI BÁO
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final Angler angler = Angler.getInstance();
    private final Blocker blocker = Blocker.getInstance();
    private final Hooker hooker = Hooker.getInstance();
    private final Drivebase drivebase = Drivebase.getInstance();
    private final Shooter shooter = Shooter.getInstance();
    private final Spinner spinner = Spinner.getInstance();
    private final Sucker sucker = Sucker.getInstance();


    public static final Joystick stick = new Joystick(0);   //STICK LÀ SỐ 0
    public static final Joystick gamepad = new Joystick(1); //GAMEPAD LÀ SỐ 1
    private static final Joystick[] sticks = {stick, gamepad};

    Command block = new Block();
    Command unblock = new Unblock();
    Command shoot = new Shoot();
    Command shootCombo = new ParallelCommandGroup(
            new Shoot(), new Intake(),
            new WaitCommand(1).andThen(new Unblock().withTimeout(3))).withInterrupt(() -> !sticks[SHOOTING_BUTTON[0]].getRawButton(SHOOTING_BUTTON[1]))
            .andThen(new Block());
    Command intake = new Intake();
    Command outtake = new Outtake();

    Command climb = new Climb();
    Command drop = new Drop();

    Command up = new AngleUp();
    Command down = new AngleDown();

    Command color = new SpinToTarget();
    Command laps = new SpinToLaps();

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     * CÁI ROBOT CONTAINER NÀY CHỨA HẾ THÔNG CON(SUBSYS), LỆNH, THIẾT BỊ OI
     */
    public RobotContainer() {
        // Configure the button bindings
        //CẤU HÌNH CÁC LIÊN KẾT NÚT
        configureButtonBindings();

    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        new JoystickButton(sticks[SHOOTING_BUTTON[0]], SHOOTING_BUTTON[1]).whenActive(shootCombo);//BẮN
        new JoystickButton(sticks[BLOCK_BUTTON[0]], BLOCK_BUTTON[1]).whileActiveOnce(block);//CHẶN BÓNG
        new JoystickButton(sticks[UNBLOCK_BUTTON[0]], UNBLOCK_BUTTON[1]).whileActiveOnce(unblock);//BỎ CHẶN
        new JoystickButton(sticks[INTAKE_BUTTON[0]], INTAKE_BUTTON[1]).whileActiveOnce(intake);//?
        new JoystickButton(sticks[OUTTAKE_BUTTON[0]], OUTTAKE_BUTTON[1]).whileActiveOnce(outtake);//?
        new JoystickButton(sticks[ANGLE_DOWN_BUTTON[0]], ANGLE_DOWN_BUTTON[1]).whileActiveOnce(down);//CHỈNH GÓC LÊN
        new JoystickButton(sticks[ANGLE_UP_BUTTON[0]], ANGLE_UP_BUTTON[1]).whileActiveOnce(up);//CHỈNH GÓC XUỐNG
        new JoystickButton(sticks[CLIMB_BUTTON[0]], CLIMB_BUTTON[1]).whenActive(climb);//CHÈO
        new JoystickButton(sticks[DROP_BUTTON[0]], DROP_BUTTON[1]).whenActive(drop);//THẢ
        new JoystickButton(sticks[SPIN_COLOR[0]], SPIN_COLOR[1]).whenActive(color);//XOAY
        new JoystickButton(sticks[SPIN_LAPS[0]], SPIN_LAPS[1]).whenActive(laps);
        //LIÊN KẾT CÁC LỆNH VỚI CÁC NÚT

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     * @RETURN ĐỂ CHẠY TRONG CHẾ ĐỘ TỰ ĐỘNG
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return shootCombo;
    }
}

//NÓI CHUNG CÁI ROBOT CONTAINER NÀY LÀ NƠI ĐỂ KHAI BÁO VÀ LIÊN KẾT CÁC CÁI BIẾN (SHOOTING, BLOCK, UNBLOCK,...) VỚI LỆNH 