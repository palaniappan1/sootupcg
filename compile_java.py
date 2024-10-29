import os
import subprocess

def compile_java(java_file):
    if not os.path.isfile(java_file):
        print("Java file does not exist.")
        return False
    
    try:
        subprocess.run(['javac', java_file], check=True)
        print("Java file compiled successfully.")
        return True
    except subprocess.CalledProcessError as e:
        print("Error during compilation:", e)
        return False

def create_jar(java_file):
    class_name = os.path.splitext(os.path.basename(java_file))[0]
    class_file = f"{class_name}.class"
    class_dir = os.path.dirname(java_file)
    
    # Check if the .class file exists
    if not os.path.isfile(os.path.join(class_dir, class_file)):
        print(f"{class_file} does not exist in {class_dir}. Cannot create JAR file.")
        return
    
    # Change to the directory containing the .class file
    os.chdir(class_dir)
    print(f"Changed working directory to: {os.getcwd()}")

    try:
        subprocess.run(['jar', 'cvf', f"{class_name}.jar", class_file], check=True)
        print(f"JAR file '{class_name}' created successfully.")
    except subprocess.CalledProcessError as e:
        print("Error creating JAR file:", e)

def main():
    java_file = "/Users/palaniappanmuthuraman/WorkSpace/SWARM-CG/benchmarks/java/cats/VirtualCalls/VC1/vc/Class.java"

    if compile_java(java_file):
        create_jar(java_file)

if __name__ == "__main__":
    main()
